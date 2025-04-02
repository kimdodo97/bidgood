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

    /**
     * Retrieves the product information for the given product identifier.
     *
     * <p>This method finds a product by its id, converts it to a {@code ProductInfo} DTO,
     * and returns it. If no product is found, a {@code ProductNotFound} exception is thrown.</p>
     *
     * @param id the unique identifier of the product
     * @return the product information as a {@code ProductInfo} DTO
     * @throws ProductNotFound if no product with the specified id exists
     */
    public ProductInfo get(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFound::new);

        return ProductInfo.fromEntity(product);
    }

    /**
     * Retrieves a list of products matching the provided search criteria.
     *
     * <p>This method queries the product repository with the given search criteria,
     * converts each product entity to its corresponding {@code ProductInfo} DTO,
     * and returns the resulting list.</p>
     *
     * @param productSearch the criteria used to filter the products
     * @return a list of {@code ProductInfo} objects for the matched products
     */
    public List<ProductInfo> getProductList(ProductSearch productSearch){
        return productRepository.getList(productSearch)
                .stream().map(ProductInfo::fromEntity).collect(Collectors.toList());
    }
}
