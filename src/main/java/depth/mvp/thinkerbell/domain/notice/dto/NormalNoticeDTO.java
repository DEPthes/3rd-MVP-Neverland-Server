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
    private boolean important;

    @Builder
    public NormalNoticeDTO(Long id, LocalDate pubDate, String title, String url, boolean important) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
        this.important = important;
    }
}
