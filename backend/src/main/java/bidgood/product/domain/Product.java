package bidgood.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String detail;

    private String origin;

    @NotNull
    private String startPrice;

    private String problem;

    private ProductStatus status;

    private LocalDateTime atAuctionEnd;

    @Builder
    public Product(Long id, String name, String detail, String origin, String startPrice, String problem, LocalDateTime atAuctionEnd) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.origin = origin;
        this.startPrice = startPrice;
        this.problem = problem;
        this.atAuctionEnd = atAuctionEnd;
    }
}
