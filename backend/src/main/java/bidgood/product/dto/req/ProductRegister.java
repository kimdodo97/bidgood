package bidgood.product.dto.req;

import bidgood.product.domain.Product;
import bidgood.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRegister {

    private String name;
    private String detail;
    private String origin;
    private BigDecimal startPrice;
    private String problem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime atAuctionStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime atAuctionEnd;

    @Builder
    public ProductRegister(String name, String detail, String origin, BigDecimal startPrice, String problem,
                           LocalDateTime atAuctionStart, LocalDateTime atAuctionEnd) {
        this.name = name;
        this.detail = detail;
        this.origin = origin;
        this.startPrice = startPrice;
        this.problem = problem;
        this.atAuctionStart = atAuctionStart;
        this.atAuctionEnd = atAuctionEnd;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .detail(detail)
                .origin(origin)
                .problem(problem)
                .startPrice(startPrice)
                .atAuctionStart(atAuctionStart)
                .atAuctionEnd(atAuctionEnd)
                .build();
    }
}
