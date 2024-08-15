package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicNoticeRepository extends JpaRepository<AcademicNotice, Long> {
    List<AcademicNotice> findByIsImportant(boolean isImportant);
}
