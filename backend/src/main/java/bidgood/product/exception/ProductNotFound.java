package bidgood.product.exception;

import bidgood.global.exception.BidGoodException;

public class ProductNotFound extends BidGoodException {
    private static String MESSAGE = "경매품이 존재하지 않습니다.";
    public ProductNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
