package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class LibraryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private String campus;

    @Builder
    public LibraryNoticeDTO(Long id, LocalDate pubDate, String title, String url, String campus) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
        this.campus = campus;
    }
}
