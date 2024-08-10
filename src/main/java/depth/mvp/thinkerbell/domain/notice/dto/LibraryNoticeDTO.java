package depth.mvp.thinkerbell.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 JSON에 포함되지 않음
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
