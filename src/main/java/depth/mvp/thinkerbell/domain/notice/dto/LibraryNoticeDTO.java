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

    // 엔티티를 DTO로 변환하는 정적 메서드
//    public static LibraryNoticeDTO fromEntity(LibraryNotice notice) {
//        return new LibraryNoticeDTO(
//                notice.getId(),
//                notice.getPubDate(),
//                notice.getTitle(),
//                notice.getUrl(),
//                notice.isImportant(),
//                notice.getCampus()
//        );
//    }
}