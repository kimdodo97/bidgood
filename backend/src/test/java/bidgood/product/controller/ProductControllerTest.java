package bidgood.product.controller;

import bidgood.auth.security.TestConfig;
import bidgood.auth.security.TestUser;
import bidgood.global.jwt.JwtProvider;
import bidgood.product.dto.req.ProductRegister;
import bidgood.user.domain.Role;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class )
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        userRepository.deleteAll();
    }

    @Test
    @TestUser
    @DisplayName("상품 등록은 상품 정보를 먼저 등록 한다.")
    void registerProductTest() throws Exception {
        //given
        User user = User.builder()
                .email("test@test.com")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String accessToken = jwtProvider.createAccessToken("test@test.com"); // 테스트용 토큰 생성

        ProductRegister productRegister = ProductRegister.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.of(2025, 3, 6, 00, 00, 00))
                .atAuctionEnd(LocalDateTime.of(2025, 3, 7, 00, 00, 00))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();

        //expect
        mockMvc.perform(post("/products/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(productRegister)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber());
    }
}