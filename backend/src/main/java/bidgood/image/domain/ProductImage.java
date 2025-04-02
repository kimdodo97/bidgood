package bidgood.image.domain;

import bidgood.product.domain.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    private String originalImageName;

    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(Long id, String imagePath, String originalImageName, String fileSize, Product product) {
        this.id = id;
        this.imagePath = imagePath;
        this.originalImageName = originalImageName;
        this.fileSize = fileSize;
        this.product = product;
    }
}
