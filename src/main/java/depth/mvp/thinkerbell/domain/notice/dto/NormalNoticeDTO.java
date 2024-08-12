package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class NormalNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean isImportant;

    @Builder
    public NormalNoticeDTO(Long id, LocalDate pubDate, String title, String url, boolean isImportant) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
        this.isImportant = isImportant;
    }
}
