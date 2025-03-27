package bidgood.image.service;

import bidgood.global.exception.BidGoodException;
import bidgood.global.exception.FileStorageException;
import bidgood.global.util.FileHandler;
import bidgood.image.domain.ProductImage;
import bidgood.image.repository.ImageRepository;
import bidgood.product.domain.Product;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.Role;
import bidgood.user.domain.SocialType;
import bidgood.user.domain.User;
import bidgood.user.exception.UserMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @Mock
    private FileHandler fileHandler;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private Product mockProduct;
    private User mockUser;

    @BeforeEach
    void init(){
        mockUser = User.builder()
                .email("test@test.com")
                .socialType(SocialType.KAKAO)
                .socialId("testId")
                .password("testPassword")
                .role(Role.USER)
                .build();

        mockProduct = Product.builder()
                .id(1L)
                .name("굿즈1")
                .detail("비드굿 굿즈")
                .origin("비드굿 공식몰")
                .problem("하자없음")
                .atAuctionStart(LocalDateTime.of(2025, 3, 6, 00, 00, 00))
                .atAuctionEnd(LocalDateTime.of(2025, 3, 7, 00, 00, 00))
                .startPrice(BigDecimal.valueOf(1000L))
                .user(mockUser)
                .build();
    }

    @Test
    @DisplayName("경매상품 이미지 등록은 성공")
    void registerProductImageSuccess() throws Exception {
        //given
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(fileHandler.saveFile(any(MultipartFile.class))).thenReturn("uploads/test.jpg");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getSize()).thenReturn(100L);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
        //when
        List<BidGoodException> results = imageService.registerProductImage("test@test.com", 1L, List.of(mockFile));
        //then
        assertThat(results).isEmpty();
        verify(imageRepository, times(1)).save(any(ProductImage.class));
    }

    @Test
    @DisplayName("경매물품 등록자와 이미지 등록 사용자가 일치하지 않으면 예외가 발생한다.")
    void whenRegisterProductImageMismatchUser() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        //when
        assertThatThrownBy(() -> imageService.registerProductImage("another@test.com", 1L, List.of()))
                .isInstanceOf(UserMismatchException.class);
    }

    @Test
    @DisplayName("이미지 저장에 실패하면 실패한 이미지의 정보를 담은 결과 리스트를 반환한다.")
    void registerProductImageFail() throws Exception {
        //given
        MultipartFile mockFile = mock(MultipartFile.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));
        when(fileHandler.saveFile(any(MultipartFile.class))).thenThrow(new FileStorageException("모킹 이미지 오지지널 네임"));


        // when
        List<BidGoodException> result = imageService.registerProductImage("test@test.com", 1L, List.of(mockFile));

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isInstanceOf(FileStorageException.class);
    }
    
    @Test
    @DisplayName("이미지를 등록하는데 상품에 대한 정보가 존재하지 않으면 실패")
    void registerProductImageNotFoundProduct() throws Exception {
        //given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        //expect
        assertThatThrownBy(() -> imageService.registerProductImage("test@test.com", 1L, List.of()))
                .isInstanceOf(ProductNotFound.class);
        
    }
}