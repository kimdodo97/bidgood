package bidgood.auth.service;

import bidgood.auth.dto.KakaoUserInfo;
import bidgood.auth.exception.OauthAuthenticationException;
import bidgood.auth.dto.KakaoAuthRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import java.util.Arrays;


@Slf4j
@Service
public class OAuthService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String INFO_URL = "https://kapi.kakao.com/v2/user/me";

    @Value("${oauth2.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${oauth2.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client-secret}")
    private String CLIENT_SECRET;

    public OAuthService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    public KakaoAuthRes getOAuthToken(String code) throws HttpClientErrorException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);


        ResponseEntity<KakaoAuthRes> response = restClient.post()
                .uri(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(KakaoAuthRes.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new OauthAuthenticationException();
        }

        return response.getBody();
    }

    public KakaoUserInfo getUserInfo(String accessToken){
        MultiValueMap<String, String> kakaoUserInfoRequest = new LinkedMultiValueMap<>();
        ResponseEntity<String> response = restClient.post()
                .uri(INFO_URL)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(kakaoUserInfoRequest)
                .retrieve()
                .toEntity(String.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new OauthAuthenticationException();
        }

        try {
            return objectMapper.readValue(response.getBody(), KakaoUserInfo.class);
        } catch (JsonProcessingException e) {
            log.info(Arrays.toString(e.getStackTrace()));
            throw new OauthAuthenticationException();
        }
    }
}
