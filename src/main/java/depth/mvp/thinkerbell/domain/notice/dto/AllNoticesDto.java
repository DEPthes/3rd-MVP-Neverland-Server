package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllNoticesDto {

    private Long id;
    private String title;
    private String tableName;

    @Builder
    public AllNoticesDto(Long id, String title, String tableName) {
        this.id = id;
        this.title = title;
        this.tableName = tableName;
    }
}
