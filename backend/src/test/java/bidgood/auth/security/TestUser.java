package bidgood.auth.security;

import bidgood.user.domain.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WithSecurityContext(factory = CustomTestUserFactory.class)
public @interface TestUser {
    String username() default "test@test.com";
    String password() default "test";
    Role role() default Role.USER;
}
