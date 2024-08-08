package depth.mvp.thinkerbell.domain.dept_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeptContactDto {

    private String campus;
    private String major;
    private String college;
    private String contact;

    @Builder
    public DeptContactDto(String campus, String major, String college, String contact) {
        this.campus = campus;
        this.major = major;
        this.college = college;
        this.contact = contact;
    }
}
