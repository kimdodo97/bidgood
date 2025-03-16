package bidgood.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginTokenRes {
    private String AccessToken;
    private String RefreshToken;
    private String email;

    @Builder
    public LoginTokenRes(String accessToken, String refreshToken, String email) {
        AccessToken = accessToken;
        RefreshToken = refreshToken;
        this.email = email;
    }
}
