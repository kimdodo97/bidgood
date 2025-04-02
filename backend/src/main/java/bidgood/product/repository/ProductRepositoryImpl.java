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
