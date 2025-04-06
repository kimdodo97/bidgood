package bidgood.product.domain;

import bidgood.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String detail;

    private String origin;

    @NotNull
    private BigDecimal startPrice;

    private String problem;

    private LocalDateTime atAuctionStart;

    private LocalDateTime atAuctionEnd;

    @Builder
    public Product(Long id, User user, String name, String detail, String origin, BigDecimal startPrice, String problem, LocalDateTime atAuctionStart, LocalDateTime atAuctionEnd) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.detail = detail;
        this.origin = origin;
        this.startPrice = startPrice;
        this.problem = problem;
        this.atAuctionStart = atAuctionStart;
        this.atAuctionEnd = atAuctionEnd;
    }

    public void setAuthor(User user){
        this.user = user;
    }
}
