package depth.mvp.thinkerbell.domain.notice.repository;


import depth.mvp.thinkerbell.domain.notice.entity.JobTrainingNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTrainingNoticeRepository extends JpaRepository<JobTrainingNotice, Long> {
}
