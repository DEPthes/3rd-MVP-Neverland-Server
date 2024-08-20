package depth.mvp.thinkerbell.domain.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AlarmDto {

    private Long id;
    private String title;
    private String noticeType;
    private boolean isViewed;
    private boolean isMarked;
    private String Url;
    private String pubDate;
}
