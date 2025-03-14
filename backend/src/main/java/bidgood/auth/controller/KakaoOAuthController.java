package bidgood.auth.controller;

import bidgood.auth.dto.KakaoAuthRes;
import bidgood.auth.dto.KakaoUserInfo;
import bidgood.auth.dto.LoginTokenRes;
import bidgood.auth.service.OAuthService;
import bidgood.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final OAuthService oAuthService;
    private final UserService userService;

    @GetMapping("oauth2/callback/kakao")
    public ResponseEntity<String> kakaoLogin(String code){
        KakaoAuthRes oAuthToken = oAuthService.getOAuthToken(code);
        KakaoUserInfo userInfo = oAuthService.getUserInfo(oAuthToken.getAccessToken());

        boolean duplicated = userService.duplicateUser(userInfo.getKakaoAccount().getEmail());
        if(!duplicated){
            userService.joinUser(userInfo.toRequest());
        }

        LoginTokenRes tokenRes = userService.login(userInfo);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", tokenRes.getRefreshToken())
                .httpOnly(true)
                //.secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일 유지
                .build();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header("Authorization",tokenRes.getAccessToken())
                .body("로그인 성공");
    }
}
