package bidgood.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected-endpoint")
public class AuthTestController {
    @GetMapping
    @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("Access granted!");
    }
}
