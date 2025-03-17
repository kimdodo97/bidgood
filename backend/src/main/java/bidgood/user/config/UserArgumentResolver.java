package bidgood.user.config;

import bidgood.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private static final int TOKEN_INDEX = 1;
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return resolve(Objects.requireNonNull(webRequest.getHeader(HttpHeaders.AUTHORIZATION)));
    }

    private UserEmailDto resolve(String authorizationHeader){
        final String jwtToken = authorizationHeader.split(" ")[TOKEN_INDEX];
        String email = jwtProvider.extractEmail(jwtToken)
                .orElse(null);

        return new UserEmailDto(email);
    }
}
