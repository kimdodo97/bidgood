package bidgood.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class KakaoAuthReq {
    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    private String code;

    @JsonProperty("client_secret")
    private String clientSecret;

    @Builder
    public KakaoAuthReq(String grantType, String clientId, String redirectUri, String code, String clientSecret) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.code = code;
        this.clientSecret = clientSecret;
    }
}
