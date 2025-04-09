package bidgood.auction.exception;

import bidgood.global.exception.BidGoodException;

public class AuctionNotFound extends BidGoodException {
    private static final String MESSAGE = "해당 경매는 없는 경매입니다.";

    public AuctionNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
