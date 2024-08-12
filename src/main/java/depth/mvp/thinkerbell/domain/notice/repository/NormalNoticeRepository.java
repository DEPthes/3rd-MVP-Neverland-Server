package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.NormalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NormalNoticeRepository  extends JpaRepository<NormalNotice, Long> {
    List<NormalNotice> findByIsImportant(boolean isImportant);
}
