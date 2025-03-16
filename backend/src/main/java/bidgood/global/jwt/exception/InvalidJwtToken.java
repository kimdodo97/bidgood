package bidgood.global.jwt.exception;

import bidgood.global.exception.BidGoodException;

public class InvalidJwtToken extends BidGoodException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidJwtToken() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
