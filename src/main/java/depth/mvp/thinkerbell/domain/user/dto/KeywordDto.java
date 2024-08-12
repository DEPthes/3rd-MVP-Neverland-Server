package depth.mvp.thinkerbell.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeywordDto {

    private String keyword;

    @Builder
    public KeywordDto(String keyword) {
        this.keyword = keyword;
    }
}
