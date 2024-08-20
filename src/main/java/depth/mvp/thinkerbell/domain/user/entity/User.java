package depth.mvp.thinkerbell.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ssaid;
    private String fcmToken;

    @Builder
    public User(String ssaid, String fcmToken) {
        this.ssaid = ssaid;
        this.fcmToken = fcmToken;
    }
}
