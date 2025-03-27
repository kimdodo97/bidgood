package bidgood.user.domain;

import bidgood.product.domain.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private SocialType socialType;

    private String socialId;

    private String refreshToken;

    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Product> products = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, SocialType socialType, String socialId, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.socialType = socialType;
        this.socialId = socialId;
        this.role = role;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
