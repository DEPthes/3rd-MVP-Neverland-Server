package depth.mvp.thinkerbell.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 JSON에 포함되지 않음
public class DormitoryEntryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private Boolean isImportant;
    private String campus;
}