package depth.mvp.thinkerbell.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademicNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private Boolean important;

    @Builder
    public AcademicNoticeDTO(Long id, LocalDate pubDate, String title, String url, Boolean important) {
        this.id = id;
        this.pubDate = pubDate;
        this.title = title;
        this.url = url;
        this.important = important;
    }
}
