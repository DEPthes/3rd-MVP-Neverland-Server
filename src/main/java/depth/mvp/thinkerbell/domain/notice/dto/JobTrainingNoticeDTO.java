package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class JobTrainingNoticeDTO {
    private Long id;
    private String company;
    private String year;
    private String semester;
    private String period;
    private String major;
    private String recrutingNum;
    private String deadline;
    private String jobName;
    private boolean marked;

}