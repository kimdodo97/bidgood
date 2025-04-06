package bidgood.auction.domain;

import bidgood.product.domain.Product;
import bidgood.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal maxPrice;

    @ManyToOne
    @JoinColumn(name="userId")
    private User maxPriceUser;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    private AuctionStatus status;

    @Builder
    public Auction(Long id, BigDecimal maxPrice, User maxPriceUser, Product product, AuctionStatus status) {
        this.id = id;
        this.maxPrice = maxPrice;
        this.maxPriceUser = maxPriceUser;
        this.product = product;
        this.status = status;
    }

    public void modifyStatus(AuctionStatus status) {
        this.status = status;
    }
}
