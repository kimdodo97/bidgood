package bidgood.product.repository;

import bidgood.product.domain.Product;
import bidgood.product.domain.QProduct;
import bidgood.product.dto.req.ProductSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static bidgood.product.domain.QProduct.product;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Retrieves a list of products that match the search criteria.
     *
     * <p>This method constructs and executes a query using JPAQueryFactory to select products whose names contain the search word
     * specified in the provided {@code ProductSearch} object. It applies pagination by limiting the number of results and 
     * setting an offset, and orders the returned products by ID in descending order.
     *
     * @param productSearch encapsulates the search word, pagination limit, and offset details
     * @return a list of products that satisfy the search criteria
     */
    @Override
    public List<Product> getList(ProductSearch productSearch) {
        return jpaQueryFactory.selectFrom(product)
                .where(product.name.contains(productSearch.getSearchWord()))
                .limit(productSearch.getSize())
                .offset(productSearch.getOffset())
                .orderBy(product.id.desc())
                .fetch();
    }
}
