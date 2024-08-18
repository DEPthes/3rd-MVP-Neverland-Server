package depth.mvp.thinkerbell.domain.notice.dto;

import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class LibraryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean marked;
    private boolean important;
    private String campus;

}