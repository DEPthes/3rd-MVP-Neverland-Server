package depth.mvp.thinkerbell.domain.dept_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeptURLDto {

    private String school;
    private String college;
    private String url;

    @Builder
    public DeptURLDto(String school, String college, String url) {
        this.school = school;
        this.college = college;
        this.url = url;
    }
}
