package bidgood.auth.exception;

import bidgood.global.exception.BidGoodException;

public class OAuthHttpException extends BidGoodException {
    private static String MESSAGE = "OAuth 인증 서버 통신 실패";
    
    public OAuthHttpException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
