package bidgood.user.service;

import bidgood.auth.dto.KakaoUserInfo;
import bidgood.auth.dto.LoginTokenRes;
import bidgood.global.jwt.JwtProvider;
import bidgood.global.util.UuidGenerator;
import bidgood.user.domain.User;
import bidgood.user.dto.UserCreateReq;
import bidgood.user.exception.UserNotFound;
import bidgood.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UuidGenerator uuidGenerator;
    private final JwtProvider jwtProvider;

    @Transactional
    public Long joinUser(UserCreateReq userCreateReq) {
        String password = uuidGenerator.generate().toString();
        User newUser = userCreateReq.toEntity(password);
        userRepository.save(newUser);

        return newUser.getId();
    }

    public boolean duplicateUser(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public LoginTokenRes login(KakaoUserInfo loginUserInfo){
        String email = loginUserInfo.getKakaoAccount().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken(email);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return LoginTokenRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .build();
    }
}
