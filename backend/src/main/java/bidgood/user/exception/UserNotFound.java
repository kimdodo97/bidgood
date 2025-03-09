package bidgood.user.exception;

import bidgood.global.exception.BidGoodException;

public class UserNotFound extends BidGoodException {
    private final static String MESSAGE = "해당 사용자가 존재하지 않습니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
