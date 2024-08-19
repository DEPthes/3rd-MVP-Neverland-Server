package depth.mvp.thinkerbell.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RecentMarkedNoticeDto {

    private String category;
    private String title;
    private LocalDate pubDate;
    private String url;

    @Builder
    public RecentMarkedNoticeDto(String category, String title, LocalDate pubDate, String url) {
        this.category = category;
        this.title = title;
        this.pubDate = pubDate;
        this.url = url;
    }
}
