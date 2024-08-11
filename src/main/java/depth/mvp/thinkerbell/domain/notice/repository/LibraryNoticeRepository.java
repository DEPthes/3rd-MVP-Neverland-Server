package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryNoticeRepository extends JpaRepository<LibraryNotice, Long> {
    Page<LibraryNotice> findAllByOrderByIsImportantDescPubDateDesc(Pageable pageable);
}
