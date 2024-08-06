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
@Table(name = "Dormitory_Entry_Notice")
public class DormitoryEntryNotice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campus;

    private boolean isImportant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;

    private String category;

    @Builder
    public DormitoryEntryNotice(String title, String url, LocalDate pubDate, String campus, boolean isImportant, Univ univ, String category) {
        this.title = title;
        this.url = url;
        this.pubDate = pubDate;
        this.campus = campus;
        this.isImportant = isImportant;
        this.univ = univ;
        this.category = category;
    }
}