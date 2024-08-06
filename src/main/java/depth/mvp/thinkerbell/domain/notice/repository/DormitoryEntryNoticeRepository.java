package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormitoryEntryNoticeRepository extends JpaRepository<DormitoryEntryNotice, Long> {
}
