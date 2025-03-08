package bidgood.auth.controller;

import bidgood.auth.dto.KakaoAuthRes;
import bidgood.auth.dto.KakaoUserInfo;
import bidgood.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final OAuthService oAuthService;

    @GetMapping("oauth2/callback/kakao")
    public String kakaoLogin(String code){
        KakaoAuthRes oAuthToken = oAuthService.getOAuthToken(code);
        KakaoUserInfo userInfo = oAuthService.getUserInfo(oAuthToken.getAccessToken());
        return "OK";
    }

}
