package bidgood.user.config;

import bidgood.global.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.http.HttpHeaders;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserArgumentResolverTest {
    private UserArgumentResolver resolver;
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = mock(JwtProvider.class);
        resolver = new UserArgumentResolver(jwtProvider);
    }

    @Test
    @DisplayName("@AuthUser 가 붙은 파라미터를 지원한다.")
    void paramWithAuthUserAnnotation() throws Exception {
        //given
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(AuthUser.class)).thenReturn(true);
        //when

        boolean result = resolver.supportsParameter(parameter);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("인증 요청에서 토큰을 통한 이메일 추출이 가능하다.")
    void resolverArgsForHttpsRequest() throws Exception {
        //given
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        String token  = "Bearer test.access.token";
        String email = "test@test.com";

        //when
        when(webRequest.getHeader("Authorization")).thenReturn(token);
        when(jwtProvider.extractEmail("test.access.token")).thenReturn(Optional.of(email));

        UserEmailDto result = (UserEmailDto) resolver.resolveArgument(null, null, webRequest, null);

        //then
        assertThat(Objects.requireNonNull(result).email()).isEqualTo(email);
    }

    @Test
    @DisplayName("Authorization 헤더가 없으면 예외가 발생한다.")
    void throwExceptionWhenNoAuthorizationHeaderForHttpRequest() throws Exception {
        //given
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        when(webRequest.getHeader("Authorization")).thenReturn(null);

        //expect
        assertThrows(NullPointerException.class, () ->
                resolver.resolveArgument(null, null, webRequest, null));
    }
}