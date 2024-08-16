package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Builder
    public AcademicScheduleDto(Long id, String title, Long univId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.univId = univId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
