package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.AcademicSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicScheduleRepository extends JpaRepository<AcademicSchedule, Long> {
}
