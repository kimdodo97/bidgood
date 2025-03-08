package bidgood.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoAuthRes {
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpireIn;

    private String scope;

    @Builder
    public KakaoAuthRes(String tokenType, String accessToken, String refreshToken, Integer expiresIn, Integer refreshTokenExpireIn, String scope) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.refreshTokenExpireIn = refreshTokenExpireIn;
        this.scope = scope;
    }
}
