package depth.mvp.thinkerbell.domain.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AlarmDto {

    private Long id;
    private String title;
    private String noticeType_korean;
    private String noticeType_english;
    private boolean isViewed;
    private boolean isMarked;
    private String Url;
    private String pubDate;
}
