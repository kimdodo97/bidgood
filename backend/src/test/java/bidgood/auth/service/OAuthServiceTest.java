package bidgood.auth.service;

import bidgood.auth.dto.KakaoAuthRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RestClientTest(value={OAuthService.class})
class OAuthServiceTest {
    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private MockRestServiceServer mockServer;

    @BeforeEach
    void clear(){
        mockServer.reset();
    }

    @Test
    @DisplayName("카카오 소셜 인증 토큰 획득이 성공한다.")
    void successGetToken() throws Exception {
        //given
        String code = "testKakaoCode";
        KakaoAuthRes expectedResponse = KakaoAuthRes.builder()
                .tokenType("bearer")
                .accessToken("kakaoAccessToken")
                .expiresIn(10000)
                .refreshToken("kakaoRefreshToken")
                .refreshTokenExpireIn(10000)
                .scope("email")
                .build();
        //when
        mockServer.expect(requestTo("https://kauth.kakao.com/oauth/token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess(
                        "{\"access_token\":\"kakaoAccessToken\",\"expires_in\":10000," +
                                "\"refresh_token\":\"kakaoRefreshToken\",\"refresh_token_expires_in\":10000," +
                                "\"scope\":\"email\",\"token_type\":\"bearer\"}",
                        MediaType.APPLICATION_JSON));
        KakaoAuthRes actualResponse = oAuthService.getOAuthToken(code);

        //then
        assertAll(
                () -> mockServer.verify(),
                () -> assertThat(actualResponse.getAccessToken()).isEqualTo(expectedResponse.getAccessToken()),
                () -> assertThat(actualResponse.getExpiresIn()).isEqualTo(expectedResponse.getExpiresIn()),
                () -> assertThat(actualResponse.getRefreshToken()).isEqualTo(expectedResponse.getRefreshToken()),
                () -> assertThat(actualResponse.getRefreshTokenExpireIn()).isEqualTo(expectedResponse.getRefreshTokenExpireIn()),
                () -> assertThat(actualResponse.getScope()).isEqualTo(expectedResponse.getScope()),
                () -> assertThat(actualResponse.getTokenType()).isEqualTo(expectedResponse.getTokenType())
        );
    }
}