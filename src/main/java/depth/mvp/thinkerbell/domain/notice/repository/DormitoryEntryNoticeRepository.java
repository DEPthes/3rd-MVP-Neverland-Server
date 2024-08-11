package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormitoryEntryNoticeRepository extends JpaRepository<DormitoryEntryNotice, Long> {
    Page<DormitoryEntryNotice> findAllByOrderByIsImportantDescPubDateDesc(Pageable pageable);
}
