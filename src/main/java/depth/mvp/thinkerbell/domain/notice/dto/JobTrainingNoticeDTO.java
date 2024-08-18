package depth.mvp.thinkerbell.domain.notice.dto;

import depth.mvp.thinkerbell.domain.notice.entity.JobTrainingNotice;
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

    // 엔티티를 DTO로 변환하는 정적 메서드
    public static JobTrainingNoticeDTO fromEntity(JobTrainingNotice notice) {
        return new JobTrainingNoticeDTO(
                notice.getId(),
                notice.getCompany(),
                notice.getYear(),
                notice.getSemester(),
                notice.getPeriod(),
                notice.getMajor(),
                notice.getRecrutingNum(),
                notice.getDeadline(),
                notice.getJobName()
        );
    }
}