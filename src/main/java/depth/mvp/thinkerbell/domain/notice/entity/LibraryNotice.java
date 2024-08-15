package depth.mvp.thinkerbell.domain.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Library_Notice")
public class LibraryNotice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campus;

    private boolean isImportant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Univ univ;

    @Builder
    public LibraryNotice(String title, String url, LocalDate pubDate, String campus, boolean isImportant,  Univ univ) {
        this.title = title;
        this.url = url;
        this.pubDate = pubDate;
        this.campus = campus;
        this.isImportant = isImportant;
        this.univ = univ;
    }
}