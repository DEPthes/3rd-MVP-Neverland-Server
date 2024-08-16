package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.JobTrainingNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobTrainingNoticeRepository extends JpaRepository<JobTrainingNotice, Long> {
    @Query("SELECT n FROM JobTrainingNotice n WHERE n.company LIKE CONCAT('%', :keyword, '%') OR n.major LIKE CONCAT('%', :keyword, '%')")
    List<JobTrainingNotice> searchByTitleOrMajor(@Param("keyword") String keyword);
}
