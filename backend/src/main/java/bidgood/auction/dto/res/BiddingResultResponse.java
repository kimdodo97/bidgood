package bidgood.auction.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BiddingResultResponse {
    private Long auctionId;
    private BigDecimal currentPrice;
    private String bidderName;

    @Builder
    public BiddingResultResponse(Long auctionId, BigDecimal currentPrice, String bidderName) {
        this.auctionId = auctionId;
        this.currentPrice = currentPrice;
        this.bidderName = bidderName;
    }
}
