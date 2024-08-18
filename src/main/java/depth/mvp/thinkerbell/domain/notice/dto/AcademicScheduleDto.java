package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AcademicScheduleDto {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean marked;

}
