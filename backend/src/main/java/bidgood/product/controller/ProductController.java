package bidgood.product.controller;

import bidgood.auth.dto.CustomUserDetail;
import bidgood.product.domain.Product;
import bidgood.product.dto.req.ProductRegister;
import bidgood.product.dto.req.ProductSearch;
import bidgood.product.dto.res.ProductInfo;
import bidgood.product.service.ProductService;
import bidgood.user.config.AuthUser;
import bidgood.user.config.UserEmailDto;
import bidgood.user.domain.User;
import bidgood.user.dto.UserProfile;
import bidgood.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerProduct(@AuthUser UserEmailDto user, @RequestBody ProductRegister productRegister) {
        Long registerId = productService.registerProduct(user.email(), productRegister);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerId);
    }

    @GetMapping()
    public ResponseEntity<List<ProductInfo>> getAllProducts(@RequestParam Integer size, @RequestParam Integer page,
                                                            ProductSearch productSearch) {
        List<ProductInfo> productList = productService.getProductList(productSearch);
        return ResponseEntity.ok(productList);
    }
}
