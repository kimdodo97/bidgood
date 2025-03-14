package bidgood.user.dto;

import bidgood.user.domain.Role;
import bidgood.user.domain.SocialType;
import bidgood.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private Long id;
    private String email;
    private String password;
    private SocialType socialType;
    private String socialId;
    private Role role;

    @Builder
    public UserProfile(Long id, String email, String password, SocialType socialType, String socialId, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.socialType = socialType;
        this.socialId = socialId;
        this.role = role;
    }

    public static UserProfile fromEntity(User user) {
        return UserProfile.builder()
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .build();
    }
}
