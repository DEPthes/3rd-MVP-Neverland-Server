package depth.mvp.thinkerbell.domain.banner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerDTO {
    private Long id;
    private String title;
    private String s3Url;

    @Builder
    public BannerDTO(Long id, String title, String s3Url) {
        this.id = id;
        this.title = title;
        this.s3Url = s3Url;
    }
}