package bidgood.auction.domain;

import bidgood.product.domain.Product;
import bidgood.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal maxPrice;

    @ManyToOne
    @JoinColumn(name="userId")
    private User maxPriceUser;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    public void updateMaxPrice(BigDecimal maxPrice,User user) {
        this.maxPrice = maxPrice;
        this.maxPriceUser = user;
    }
}
