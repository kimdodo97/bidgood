package bidgood.user.service;

import bidgood.global.util.UuidGenerator;
import bidgood.user.domain.SocialType;
import bidgood.user.domain.User;
import bidgood.user.dto.UserCreateReq;
import bidgood.user.exception.UserNotFound;
import bidgood.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UuidGenerator uuidGenerator;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("")
    void joinUserTest() throws Exception {
        /*
        Mocking 말고 좀 더 깔끔한 테스트 코드 없을까 고민해봐야 할듯
         */
        //given
        UUID fixUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(uuidGenerator.generate()).thenReturn(fixUuid);

        UserCreateReq userCreateReq = UserCreateReq.builder()
                .email("test@test.com")
                .socialType(SocialType.KAKAO)
                .socialId("1234")
                .build();

        User mockUser = userCreateReq.toEntity(fixUuid.toString());
        ReflectionTestUtils.setField(mockUser, "id", 1L);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            ReflectionTestUtils.setField(user, "id", 1L); // 저장된 객체의 ID 설정
            return user;
        });

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        //when
        Long userId = userService.joinUser(userCreateReq);
        User result = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        //then
        assertEquals(userCreateReq.getEmail(),result.getEmail());
        assertEquals(userCreateReq.getSocialType(),result.getSocialType());
        assertEquals(userCreateReq.getSocialId(),result.getSocialId());
        assertEquals(fixUuid.toString(), result.getPassword());
    }
}