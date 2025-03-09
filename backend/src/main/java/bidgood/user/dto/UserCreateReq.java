package bidgood.user.dto;

import bidgood.user.domain.SocialType;
import bidgood.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserCreateReq {
    private String email;
    private SocialType socialType;
    private String socialId;

    @Builder
    public UserCreateReq(String email, SocialType socialType, String socialId) {
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    public User toEntity(String password){
        return User.builder()
                .email(email)
                .socialType(socialType)
                .password(password)
                .socialId(socialId)
                .socialType(socialType)
                .build();
    }
}
