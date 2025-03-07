package bidgood.product.service;

import bidgood.product.domain.Product;
import bidgood.product.domain.ProductStatus;
import bidgood.product.dto.req.ProductRegister;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("경매품은 신규 등록이 가능하다.")
    void registerProductTest() throws Exception {
        //given
        ProductRegister productRegister = ProductRegister.builder()
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.of(2025, 3, 6, 00, 00, 00))
                .atAuctionEnd(LocalDateTime.of(2025, 3, 7, 00, 00, 00))
                .startPrice(BigDecimal.valueOf(1000L))
                .build();

        //when
        Long resultId = productService.createProduct(productRegister);
        Product result = productRepository.findById(resultId)
                .orElseThrow(ProductNotFound::new);

        //then
        assertEquals(productRegister.getName(),result.getName());
        assertEquals(productRegister.getDetail(),result.getDetail());
        assertEquals(productRegister.getOrigin(),result.getOrigin());
        assertEquals(productRegister.getProblem(),result.getProblem());
        assertTrue(productRegister.getStartPrice().compareTo(result.getStartPrice()) == 0);
        assertEquals(productRegister.getAtAuctionStart(),result.getAtAuctionStart());
        assertEquals(productRegister.getAtAuctionEnd(),result.getAtAuctionEnd());
        assertEquals(ProductStatus.PREPARE,result.getStatus());
    }
}