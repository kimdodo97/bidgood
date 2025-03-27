package bidgood.auth.security;

import bidgood.global.jwt.JwtProvider;
import bidgood.user.config.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public UserArgumentResolver userArgumentResolver(JwtProvider jwtProvider) {
        return new UserArgumentResolver(jwtProvider);
    }
}