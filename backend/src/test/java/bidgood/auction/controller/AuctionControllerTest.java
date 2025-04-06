package bidgood.auction.controller;

import bidgood.auction.domain.Auction;
import bidgood.auction.domain.AuctionStatus;
import bidgood.auction.repository.AuctionRepository;
import bidgood.auth.security.TestConfig;
import bidgood.global.jwt.JwtProvider;
import bidgood.product.domain.Product;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.Role;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class )
@ActiveProfiles("test")
class AuctionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    void init() {
        auctionRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("경매는 수동으로도 시작이 가능합니다.")
    void updateAuctionStart() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String accessToken = jwtProvider.createAccessToken("test@test.com"); // 테스트용 토큰 생성
        Product product = Product.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.of(2025, 3, 6, 00, 00, 00))
                .atAuctionEnd(LocalDateTime.of(2025, 3, 7, 00, 00, 00))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();
        product.setAuthor(user);
        productRepository.save(product);
        Auction auction = Auction.builder()
                .status(AuctionStatus.PREPARE)
                .maxPrice(product.getStartPrice())
                .product(product)
                .build();
        auctionRepository.save(auction);

        //expect
        mockMvc.perform(patch("/auction/start/"+auction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("경매는 수동으로도 마감이 가능합니다.")
    void updateAuctionClose() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String accessToken = jwtProvider.createAccessToken("test@test.com"); // 테스트용 토큰 생성
        Product product = Product.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.of(2025, 3, 6, 00, 00, 00))
                .atAuctionEnd(LocalDateTime.of(2025, 3, 7, 00, 00, 00))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();
        product.setAuthor(user);
        productRepository.save(product);
        Auction auction = Auction.builder()
                .status(AuctionStatus.PREPARE)
                .maxPrice(product.getStartPrice())
                .product(product)
                .build();
        auctionRepository.save(auction);

        //expect
        mockMvc.perform(patch("/auction/close/"+auction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
}