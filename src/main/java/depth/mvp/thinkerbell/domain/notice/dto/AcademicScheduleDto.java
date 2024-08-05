package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class AcademicScheduleDto {

    private Long id;
    private String title;
    private String schedule;
    private Long univId;

}
