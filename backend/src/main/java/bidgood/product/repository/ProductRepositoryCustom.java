package bidgood.product.repository;

import bidgood.product.domain.Product;
import bidgood.product.dto.req.ProductSearch;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getList(ProductSearch productSearch);
}
