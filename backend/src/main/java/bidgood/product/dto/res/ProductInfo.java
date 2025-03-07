package bidgood.product.dto.res;

import bidgood.product.domain.Product;
import bidgood.product.domain.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductInfo {
    private Long id;
    private String name;
    private String detail;
    private String origin;
    private BigDecimal startPrice;
    private String problem;
    private ProductStatus status;
    private LocalDateTime atAuctionStart;
    private LocalDateTime atAuctionEnd;

    @Builder
    public ProductInfo(Long id, String name, String detail, String origin, BigDecimal startPrice, String problem, ProductStatus status, LocalDateTime atAuctionStart, LocalDateTime atAuctionEnd) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.origin = origin;
        this.startPrice = startPrice;
        this.problem = problem;
        this.status = status;
        this.atAuctionStart = atAuctionStart;
        this.atAuctionEnd = atAuctionEnd;
    }

    public static ProductInfo fromEntity(Product product) {
        return ProductInfo.builder()
                .id(product.getId())
                .name(product.getName())
                .detail(product.getDetail())
                .origin(product.getOrigin())
                .startPrice(product.getStartPrice())
                .problem(product.getProblem())
                .status(product.getStatus())
                .atAuctionStart(product.getAtAuctionStart())
                .atAuctionEnd(product.getAtAuctionEnd())
                .build();
    }
}
