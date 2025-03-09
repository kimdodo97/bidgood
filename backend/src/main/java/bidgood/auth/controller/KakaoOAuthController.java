package bidgood.auth.controller;

import bidgood.auth.dto.KakaoAuthRes;
import bidgood.auth.dto.KakaoUserInfo;
import bidgood.auth.service.OAuthService;
import bidgood.user.domain.SocialType;
import bidgood.user.dto.UserCreateReq;
import bidgood.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final OAuthService oAuthService;
    private final UserService userService;

    @GetMapping("oauth2/callback/kakao")
    public String kakaoLogin(String code){
        KakaoAuthRes oAuthToken = oAuthService.getOAuthToken(code);
        KakaoUserInfo userInfo = oAuthService.getUserInfo(oAuthToken.getAccessToken());

        boolean duplicated = userService.duplicateUser(userInfo.getKakaoAccount().getEmail());
        if(!duplicated){
            userService.joinUser(userInfo.toRequest());
            //로그인 화면 redirect
            return "Main";
        }

        return "Login";
    }

}
