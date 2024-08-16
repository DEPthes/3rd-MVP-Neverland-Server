package depth.mvp.thinkerbell.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import lombok.*;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 JSON에 포함되지 않음
@AllArgsConstructor
public class LibraryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private Boolean important;
    private String campus;

    // 엔티티를 DTO로 변환하는 정적 메서드
    public static LibraryNoticeDTO fromEntity(LibraryNotice notice) {
        return new LibraryNoticeDTO(
                notice.getId(),
                notice.getPubDate(),
                notice.getTitle(),
                notice.getUrl(),
                notice.isImportant(),
                notice.getCampus()
        );
    }
}