package depth.mvp.thinkerbell.domain.notice.entity;

import depth.mvp.thinkerbell.domain.common.entity.Univ;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Job_Training_Notice")
public class JobTrainingNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String year;
    private String semester;
    private String period;
    private String major;
    private String recrutingNum;
    private String deadline;
    private String jobName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;

    @Builder
    public JobTrainingNotice(String company, String year, String semester, String period, String major,
                             String recrutingNum, String deadline, String jobName, Univ univ) {
        this.company = company;
        this.year = year;
        this.semester = semester;
        this.period = period;
        this.recrutingNum = recrutingNum;
        this.deadline = deadline;
        this.jobName = jobName;
        this.univ = univ;
    }
}