package bidgood.auction.exception;

import bidgood.global.exception.BidGoodException;

public class MaxPriceIsInvalid extends BidGoodException {
    private static final String MESSAGE = "요청 경매가가 최고가가 아닙니다.";

    public MaxPriceIsInvalid() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
