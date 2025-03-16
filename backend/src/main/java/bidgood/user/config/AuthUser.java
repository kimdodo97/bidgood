package bidgood.user.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  // 메서드 파라미터에 적용
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthUser {

}
