package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class EventNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    @Builder
    public EventNoticeDTO(Long id, LocalDate pubDate, String title, String url) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
    }
}