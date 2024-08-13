package depth.mvp.thinkerbell.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmDto {

    private Long id;
    private String title;
    private String noticeType;
    private Boolean isViewed;
    private String Url;
    private String pubDate;


    @Builder
    public AlarmDto(Long id, String title, String noticeType, Boolean isViewed, String Url, String pubDate) {
        this.id = id;
        this.title = title;
        this.noticeType = noticeType;
        this.isViewed = isViewed;
        this.Url = Url;
        this.pubDate = pubDate;
    }
}
