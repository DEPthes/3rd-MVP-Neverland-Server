package depth.mvp.thinkerbell.domain.notice.dto;

import depth.mvp.thinkerbell.domain.notice.entity.TeachingNotice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class TeachingNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean marked;
    private boolean important;

//    public static TeachingNoticeDTO fromEntity(TeachingNotice notice) {
//        return new TeachingNoticeDTO(
//                notice.getId(),
//                notice.getPubDate(),
//                notice.getTitle(),
//                notice.getUrl(),
//                notice.isImportant()
//        );
//    }
}
