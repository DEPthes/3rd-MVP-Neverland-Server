package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DormitoryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean isImportant;
    private String category;
}