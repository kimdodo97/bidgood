package bidgood.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class KakaoUserInfo {
    private Long id;
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public class Properties {
        private String nickname;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class KakaoAccount {
        private String email;
    }
}