package bidgood.auction.exception;

import bidgood.global.exception.BidGoodException;

public class AlreadyMaxPriceBidder extends BidGoodException {
    private static final String MESSAGE = "이미 최고가를 등록한 입찰자 입니다.";

    public AlreadyMaxPriceBidder() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 409;
    }
}
