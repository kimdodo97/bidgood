package bidgood.image.service;

import bidgood.global.exception.BidGoodException;
import bidgood.global.exception.FileStorageException;
import bidgood.global.exception.FileUploadException;
import bidgood.global.util.FileHandler;
import bidgood.image.domain.ProductImage;
import bidgood.image.repository.ImageRepository;
import bidgood.product.domain.Product;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import bidgood.user.dto.UserProfile;
import bidgood.user.exception.UserMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final FileHandler fileHandler;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public List<BidGoodException> registerProductImage(String email, Long productId , List<MultipartFile> images){
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        if(!product.getUser().getEmail().equals(email)){
            throw new UserMismatchException();
        }

        List<BidGoodException> errors = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                String savePath = fileHandler.saveFile(image);
                ProductImage productImage = ProductImage.builder()
                        .product(product)
                        .fileSize(String.valueOf(image.getSize()))
                        .originalImageName(image.getOriginalFilename())
                        .imagePath(savePath)
                        .build();

                imageRepository.save(productImage);
            } catch (FileStorageException e) {
                errors.add(e);
            }
        }

        return errors;
    }

}
