package bidgood.auth.exception;

import bidgood.global.exception.BidGoodException;

public class OauthAuthenticationException extends BidGoodException {
    private static final String MESSAGE = "소셜 계정 인증토큰 발급 실패";
    
    public OauthAuthenticationException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
