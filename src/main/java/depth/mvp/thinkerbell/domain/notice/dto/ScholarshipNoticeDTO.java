package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ScholarshipNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean marked;
    @Builder
    public ScholarshipNoticeDTO(Long id, LocalDate pubDate, String title, boolean marked, String url) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
        this.marked = marked;
    }
}
