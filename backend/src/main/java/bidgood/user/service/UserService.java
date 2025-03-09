package bidgood.user.service;

import bidgood.global.util.UuidGenerator;
import bidgood.user.domain.User;
import bidgood.user.dto.UserCreateReq;
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

    @Transactional
    public Long joinUser(UserCreateReq userCreateReq) {
        String password = uuidGenerator.generate().toString();
        User newUser = userCreateReq.toEntity(password);
        userRepository.save(newUser);

        return newUser.getId();
    }
}
