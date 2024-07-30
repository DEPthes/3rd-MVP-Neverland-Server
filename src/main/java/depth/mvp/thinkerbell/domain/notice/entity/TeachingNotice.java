package depth.mvp.thinkerbell.domain.notice.entity;

import depth.mvp.thinkerbell.domain.common.entity.BaseEntity;
import depth.mvp.thinkerbell.domain.common.entity.Univ;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Teaching_Notice")
public class TeachingNotice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isImportant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;

    @Builder
    public TeachingNotice(String title, String url, LocalDate pubDate, boolean isImportant, Univ univ) {
        this.title = title;
        this.url = url;
        this.pubDate = pubDate;
        this.isImportant = isImportant;
        this.univ = univ;
    }
}