package bidgood.global.jwt.filter;

import bidgood.global.jwt.JwtProvider;
import bidgood.global.util.UuidGenerator;
import bidgood.user.domain.Role;
import bidgood.user.domain.SocialType;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Disabled("테스트를 비활성화")
class JwtAuthenticationFilterTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    private String validAccessToken;
    private String validRefreshToken;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@gmail.com")
                .socialType(SocialType.KAKAO)
                .socialId("testId")
                .password("testPassword")
                .role(Role.USER)
                .build();
        testUser = userRepository.save(testUser);

        validAccessToken = jwtProvider.createAccessToken(testUser.getEmail());
        validRefreshToken = jwtProvider.createRefreshToken(testUser.getEmail());

        testUser.updateRefreshToken(validRefreshToken);
        userRepository.save(testUser);
    }
    
    @Test
    @DisplayName("유효한 Access 토큰으로 인증 API 엔드포인트에 접근시 200 응답")
    void allowAccessWithValidToken() throws Exception {
        mockMvc.perform(get("/api/protected-endpoint")
                        .header("Access", "Bearer " + validAccessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("만료된 리프레시 토큰은 401 응답")
    void returnUnauthorizedExpiredRefreshToken() throws Exception {
        //given
        String expiredToken  = "expired.refresh.token";

        MockCookie refreshTokenCookie = new MockCookie("refresh_token", expiredToken);
        refreshTokenCookie.setHttpOnly(true);

        when(jwtProvider.isTokenValid(expiredToken)).thenReturn(false); // mock 객체에서 메소드 호출

        mockMvc.perform(get("/api/protected-endpoint")
                        .cookie(refreshTokenCookie)
                .header("Refresh","TRUE"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("")
    void returnUnauthorizedExpiredAccess() throws Exception {
        //given
        String expiredToken = "expired.access.token";
        String newAccessToken = jwtProvider.createAccessToken(testUser.getEmail());

        when(jwtProvider.isTokenValid(expiredToken)).thenReturn(false);
        when(jwtProvider.createAccessToken(testUser.getEmail())).thenReturn(newAccessToken);

        // perform the request using MockMvc
        mockMvc.perform(get("/api/protected-endpoint")
                        .header("Authorization", "Bearer " + expiredToken)) // Authorization 헤더를 통해 Access Token 전달
                .andExpect(status().isUnauthorized()); // 401 Unauthorized 응답 확인


    }
    @Test
    @DisplayName("유효한 리프레시 토큰을 통해서 엑세스 토큰 발급 시 200 응답")
    void sendNewAccessTokenIfValidRefresh() throws Exception {
        //given
        Cookie refreshTokenCookie = new Cookie("refresh_token", validRefreshToken);
        refreshTokenCookie.setHttpOnly(true);

        MockHttpServletResponse response = mockMvc.perform(get("/api/protected-endpoint")
                        .cookie(refreshTokenCookie)
                        .header("Refresh", "TRUE"))
                .andReturn()
                .getResponse();
        //expect
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(response.getHeader("Access")).isNotBlank();
    }
}