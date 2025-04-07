package bidgood.auction.exception;

import bidgood.global.exception.BidGoodException;

public class AuctionNotAvailableForBidding extends BidGoodException {
    private static final String MESSAGE = "경매 입찰이 불가능한 경매 상태입니다.";

    public AuctionNotAvailableForBidding() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
