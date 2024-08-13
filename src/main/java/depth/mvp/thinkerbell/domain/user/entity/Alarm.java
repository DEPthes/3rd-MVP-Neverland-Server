package depth.mvp.thinkerbell.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name = "Alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long noticeID;
    private String noticeType;
    private String title;
    private String keyword;
    private Boolean isViewed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Alarm(Long noticeID, String noticeType, User user, String title, String keyword) {
        this.noticeID = noticeID;
        this.noticeType = noticeType;
        this.user = user;
        this.title = title;
        this.keyword = keyword;
    }
}
