package bidgood.global.config;

import bidgood.global.jwt.JwtProvider;
import bidgood.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final UserRepository userRepository;
    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(userRepository);
    }
}
