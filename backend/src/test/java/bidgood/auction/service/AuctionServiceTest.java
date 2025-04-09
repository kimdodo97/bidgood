package bidgood.auction.service;

import bidgood.auction.domain.Auction;
import bidgood.auction.domain.AuctionStatus;
import bidgood.auction.dto.req.BiddingRequest;
import bidgood.auction.dto.res.BiddingResultResponse;
import bidgood.auction.exception.AuctionNotFound;
import bidgood.auction.repository.AuctionRepository;
import bidgood.product.domain.Product;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AuctionServiceTest {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Scheduler scheduler;

    @MockitoBean
    private SimpMessagingTemplate messagingTemplate;

    @AfterEach
    void clean(){
        auctionRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("경매는 상품에 기반해 등록이 된다.")
    void registerAuction() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusSeconds(5);
        LocalDateTime endTime = now.plusSeconds(10);

        User user = User.builder()
                .email("test@test.com")
                .build();
        userRepository.save(user);

        Product product = Product.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(startTime)
                .atAuctionEnd(endTime)
                .startPrice(BigDecimal.valueOf(1000L))
                .build();
        product.setAuthor(user);
        productRepository.save(product);
        //when
        auctionService.registerAuction(product.getId());

        //then
        Auction result = auctionRepository.findById(product.getId())
                .orElseThrow(AuctionNotFound::new);

        assertEquals(AuctionStatus.PREPARE, result.getStatus());

        JobKey startKey = JobKey.jobKey("start-" + result.getId(), "auction");
        JobKey endKey = JobKey.jobKey("end-" + result.getId(), "auction");

        assertTrue(scheduler.checkExists(startKey));
        assertTrue(scheduler.checkExists(endKey));
    }

    @Test
    @DisplayName("경매는 시작 시간에 맞게 스케줄러가 동작한다.")
    void scheduleStartAuction() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .build();
        userRepository.save(user);
        Product product = Product.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.now().plusSeconds(3))
                .atAuctionEnd(LocalDateTime.now().plusSeconds(6))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();
        product.setAuthor(user);
        productRepository.save(product);
        Auction auction = auctionService.registerAuction(product.getId());
        //when
        Thread.sleep(4000);
        //then
        Auction result = auctionRepository.findById(auction.getId())
                .orElseThrow(AuctionNotFound::new);

        assertEquals(AuctionStatus.SALE, result.getStatus());
    }

    @Test
    @DisplayName("경매는 마감 시간에 맞게 스케줄러가 동작한다.")
    void scheduleCloseAuction() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .build();
        userRepository.save(user);
        Product product = Product.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.now().plusSeconds(1))
                .atAuctionEnd(LocalDateTime.now().plusSeconds(2))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();
        product.setAuthor(user);
        productRepository.save(product);
        Auction auction = auctionService.registerAuction(product.getId());
        //when
        Thread.sleep(3000);
        //then
        Auction result = auctionRepository.findById(auction.getId())
                .orElseThrow(AuctionNotFound::new);

        assertEquals(AuctionStatus.COMPLETE, result.getStatus());
    }

    @Test
    @DisplayName("가장 높은 경매액을 입력한 사용자가 경매에 성공한다.")
    void biddingSuccess() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .build();

        userRepository.save(user);

        Auction auction = Auction.builder()
                .maxPrice(BigDecimal.valueOf(1000L))
                .status(AuctionStatus.SALE)
                .build();
        auctionRepository.save(auction);
        BiddingRequest biddingRequest = BiddingRequest.builder()
                .auctionId(auction.getId())
                .price(BigDecimal.valueOf(1100L))
                .build();

        //when
        auctionService.bidding(biddingRequest,user.getEmail());

        //then
        Auction result = auctionRepository.findById(auction.getId())
                .orElseThrow(AuctionNotFound::new);
        assertTrue(biddingRequest.getPrice().compareTo(result.getMaxPrice()) == 0);
        verify(messagingTemplate, times(1))
                .convertAndSend(eq("/sub/auction/" + auction.getId()), any(BiddingResultResponse.class));
    }

    @Test
    @DisplayName("동시에 여러 사용자가 입찰하면 가장 높은 금액이 입찰 최고가로 선택된다.")
    void concurrentBidding() throws Exception {
        //given
        Auction auction = Auction.builder()
                .maxPrice(BigDecimal.valueOf(1000L))
                .status(AuctionStatus.SALE)
                .build();
        auctionRepository.save(auction);
        Long auctionId = auction.getId();

        int userCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(userCount);
        ExecutorService executor = Executors.newFixedThreadPool(userCount);

        List<String> emails = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            String email = "user" + i + "@test.com";
            emails.add(email);
            userRepository.save(User.builder().email(email).build());
        }
        //when
        for (int i = 0; i < userCount; i++) {
            int bidValue = 11000 + i * 100; // 금액 11000부터 100원씩 증가
            String email = emails.get(i);
            executor.execute(() -> {
                try {
                    auctionService.bidding(
                            new BiddingRequest(auctionId, new BigDecimal(bidValue)), email);
                } catch (Exception e) {
                    System.out.println("[" + email + "] 입찰 실패: " + e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executor.shutdown();
        //then
        Auction result = auctionRepository.findById(auctionId).orElseThrow();
        System.out.println("최종 낙찰가: " + result.getMaxPrice());

        BigDecimal expected = new BigDecimal(11000 + (userCount - 1) * 100); // 제일 높은 입찰가
        assertTrue(expected.compareTo(result.getMaxPrice()) == 0);
    }

    @Test
    @DisplayName("동시에 여러 사용자가 동일금액을 입찰하면 가장 처음 유저만 입찰된다")
    void samePriceBiddingOnlyOneUserSuccess() throws Exception {
        //given
        Auction auction = Auction.builder()
                .maxPrice(BigDecimal.valueOf(1000L))
                .status(AuctionStatus.SALE)
                .build();
        auctionRepository.save(auction);
        Long auctionId = auction.getId();

        int userCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(userCount);
        ExecutorService executor = Executors.newFixedThreadPool(userCount);

        List<String> emails = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            String email = "user" + i + "@test.com";
            emails.add(email);
            userRepository.save(User.builder().email(email).build());
        }

        List<String> successEmails = Collections.synchronizedList(new ArrayList<>());
        BigDecimal targetPrice = BigDecimal.valueOf(2000L);
        //when
        for (String email : emails) {
            executor.submit(() -> {
                try {
                    auctionService.bidding(new BiddingRequest(auctionId, targetPrice), email);
                    successEmails.add(email); // 성공한 사용자 기록
                } catch (Exception ignored) {
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executor.shutdown();
        //then
        Auction result = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFound::new);

        assertTrue(targetPrice.compareTo(result.getMaxPrice()) == 0);
        assertThat(successEmails).contains(result.getMaxPriceUser().getEmail());
        assertThat(successEmails.size()).isEqualTo(1);
    }
}