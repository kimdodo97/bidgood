package bidgood.image.controller;

import bidgood.global.exception.BidGoodException;
import bidgood.image.service.ImageService;
import bidgood.user.config.AuthUser;
import bidgood.user.config.UserEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<List<BidGoodException>> registerProduct(@AuthUser UserEmailDto user, @PathVariable Long productId,
                                                @RequestParam("images")List<MultipartFile> images) {
        List<BidGoodException> saveResult = imageService.registerProductImage(user.email(), productId, images);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveResult);
    }
}
