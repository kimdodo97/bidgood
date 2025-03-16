package bidgood.global.jwt.filter;

import bidgood.auth.dto.CustomUserDetail;
import bidgood.global.jwt.JwtProvider;
import bidgood.global.util.UuidGenerator;
import bidgood.user.domain.User;
import bidgood.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UuidGenerator uuidGenerator;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(LOGIN_URL) || request.getRequestURI().equals(LOGOUT_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> extractRefresh = jwtProvider.extractRefreshToken(request);

        if(extractRefresh.isPresent()) {
            boolean tokenValid = jwtProvider.isTokenValid(extractRefresh.get());
            if(!tokenValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"errorCode\": \"REFRESH_TOKEN_EXPIRED\", \"message\": \"Refresh 토큰 만기 로그인 필요\"}");
                return;
            }
        }

        String refreshToken = jwtProvider.extractRefreshToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null);
        if(refreshToken != null) {
            sendNewAccessToken(response,refreshToken);
            return;
        }

        authenticationAccessToken(request, response, filterChain);
    }

    private void sendNewAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    jwtProvider.sendAccessAndRefreshToken(response,
                            jwtProvider.createAccessToken(user.getEmail()),
                            refreshToken);
                    log.info("[ACCESS TOKEN]재발급 성공 - {}",user.getEmail());
                });
    }

    private void authenticationAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> extractedAccessToken = jwtProvider.extractAccessToken(request);
        if(extractedAccessToken.filter(jwtProvider::isTokenValid).isPresent()) {
            String accessToken = extractedAccessToken.get();
            String email = jwtProvider.extractEmail(accessToken).orElse(null);

            if(email != null) {
                userRepository.findByEmail(email)
                        .ifPresent(this::saveAuthentication);
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        filterChain.doFilter(request,response);
    }

    public void saveAuthentication(User myUser) {
        String password = myUser.getPassword();
        if (password == null) {
            password = uuidGenerator.generate().toString();
        }

        UserDetails userDetailsUser = new CustomUserDetail(myUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //컨트롤러에서 인증할 때는어노테이션 사용
    }
}
