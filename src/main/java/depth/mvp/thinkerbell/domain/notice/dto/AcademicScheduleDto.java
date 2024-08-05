package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AcademicScheduleDto {

    private Long id;
    private String title;
    private Long univId;
    private LocalDate startDate;
    private LocalDate endDate;

}
