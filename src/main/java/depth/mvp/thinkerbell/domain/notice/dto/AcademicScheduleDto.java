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

    @Builder
    public AcademicScheduleDto(Long id, String title, String schedule, Long univId) {
        this.id = id;
        this.title = title;
        this.schedule = schedule;
        this.univId = univId;
    }
}
