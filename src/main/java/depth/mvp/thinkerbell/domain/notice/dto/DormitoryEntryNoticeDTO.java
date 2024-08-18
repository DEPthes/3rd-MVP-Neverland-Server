package depth.mvp.thinkerbell.domain.notice.dto;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class DormitoryEntryNoticeDTO {
    private Long id;
    private LocalDate pubDate;
    private String title;
    private String url;
    private boolean marked;
    private boolean important;
    private String campus;

    // 엔티티를 DTO로 변환하는 정적 메서드
//    public static DormitoryEntryNoticeDTO fromEntity(DormitoryEntryNotice notice) {
//        return new DormitoryEntryNoticeDTO(
//                notice.getId(),
//                notice.getPubDate(),
//                notice.getTitle(),
//                notice.getUrl(),
//                notice.isImportant(),
//                notice.getCampus()
//        );
//    }
}