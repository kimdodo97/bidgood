package bidgood.product.dto.res;

import bidgood.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductInfo {
    private Long id;

    private String name;

    private String detail;

    private String origin;

    private BigDecimal startPrice;

    private String problem;

    private LocalDateTime atAuctionStart;

    private LocalDateTime atAuctionEnd;

    @Builder
    public ProductInfo(Long id, String name, String detail, String origin, BigDecimal startPrice, String problem, LocalDateTime atAuctionStart, LocalDateTime atAuctionEnd) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.origin = origin;
        this.startPrice = startPrice;
        this.problem = problem;
        this.atAuctionStart = atAuctionStart;
        this.atAuctionEnd = atAuctionEnd;
    }


    public static ProductInfo fromEntity(Product product){
        return ProductInfo.builder()
                .id(product.getId())
                .name(product.getName())
                .detail(product.getDetail())
                .origin(product.getOrigin())
                .startPrice(product.getStartPrice())
                .problem(product.getProblem())
                .atAuctionStart(product.getAtAuctionStart())
                .build();
    }
}
