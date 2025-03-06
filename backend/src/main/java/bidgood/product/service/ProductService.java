package bidgood.product.service;

import bidgood.product.domain.Product;
import bidgood.product.dto.req.ProductRegister;
import bidgood.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(ProductRegister productRegister) {
        Product product = productRegister.toEntity();
        return productRepository.save(product).getId();
    }
}
