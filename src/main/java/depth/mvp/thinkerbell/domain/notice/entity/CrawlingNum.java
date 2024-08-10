package depth.mvp.thinkerbell.domain.notice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Crawling_Num")
public class CrawlingNum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long noticeID;
    private String noticeType;

    @Builder
    public CrawlingNum(Long noticeID, String noticeType) {
        this.noticeID = noticeID;
        this.noticeType = noticeType;
    }
}
