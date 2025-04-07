package bidgood.auction.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BiddingRequest {
    private Long auctionId;
    private BigDecimal price;

    @Builder
    public BiddingRequest(Long auctionId, BigDecimal price) {
        this.auctionId = auctionId;
        this.price = price;
    }
}
