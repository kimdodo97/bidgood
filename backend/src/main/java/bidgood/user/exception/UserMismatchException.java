package bidgood.user.exception;

import bidgood.global.exception.BidGoodException;

public class UserMismatchException extends BidGoodException {
    private static String MESSAGE = "게시글과 사용자가 일치하지 않습니다.";

    public UserMismatchException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
