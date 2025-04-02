package bidgood.product.service;

import bidgood.product.domain.Product;
import bidgood.product.dto.req.ProductRegister;
import bidgood.product.dto.req.ProductSearch;
import bidgood.product.dto.res.ProductInfo;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.User;
import bidgood.user.exception.UserNotFound;
import bidgood.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long registerProduct(String email, ProductRegister productRegister) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        Product product = productRegister.toEntity();
        product.setAuthor(user);

        return productRepository.save(product).getId();
    }

    public ProductInfo get(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFound::new);

        return ProductInfo.fromEntity(product);
    }

    public List<ProductInfo> getProductList(ProductSearch productSearch){
        return productRepository.getList(productSearch)
                .stream().map(ProductInfo::fromEntity).collect(Collectors.toList());
    }
}
