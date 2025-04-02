package bidgood.product.repository;

import bidgood.product.domain.Product;
import bidgood.product.dto.req.ProductSearch;

import java.util.List;

public interface ProductRepositoryCustom {
    /**
 * Retrieves a list of products matching the provided search criteria.
 *
 * @param productSearch the criteria used to filter the products
 * @return a list of products that satisfy the search criteria
 */
List<Product> getList(ProductSearch productSearch);
}
