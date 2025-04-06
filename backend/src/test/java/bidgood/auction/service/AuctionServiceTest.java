package bidgood.auction.service;

import bidgood.auction.domain.Auction;
import bidgood.auction.domain.AuctionStatus;
import bidgood.auction.exception.AuctionNotFound;
import bidgood.auction.repository.AuctionRepository;
import bidgood.product.domain.Product;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
}