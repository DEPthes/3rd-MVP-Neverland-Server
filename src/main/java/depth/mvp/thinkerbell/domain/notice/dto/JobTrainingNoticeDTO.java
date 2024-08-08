package depth.mvp.thinkerbell.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JobTrainingNoticeDTO {
    private String company;
    private String year;
    private String semester;
    private String period;
    private String major;
    private String recrutingNum;
    private String deadline;
    private String jobName;

    @Builder
    public JobTrainingNoticeDTO(String company, String year, String semester, String period, String major, String recrutingNum, String deadline, String jobName){
        this.company = company;
        this.year = year;
        this.semester = semester;
        this.period = period;
        this.major = major;
        this.recrutingNum = recrutingNum;
        this.deadline = deadline;
        this.jobName = jobName;
    }
}