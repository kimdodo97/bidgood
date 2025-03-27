package bidgood.auth.security;

import bidgood.auth.dto.CustomUserDetail;
import bidgood.user.domain.Role;
import bidgood.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.List;

public class CustomTestUserFactory implements WithSecurityContextFactory<TestUser> {
    @Override
    public SecurityContext createSecurityContext(TestUser testUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .email(testUser.username())
                .password(testUser.password())
                .role(Role.USER)
                .build();

        CustomUserDetail principal = new CustomUserDetail(user);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities()));

        return context;
    }
}
