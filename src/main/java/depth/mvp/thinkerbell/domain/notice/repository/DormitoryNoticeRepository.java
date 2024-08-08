package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormitoryNoticeRepository extends JpaRepository<DormitoryNotice, Long> {
}