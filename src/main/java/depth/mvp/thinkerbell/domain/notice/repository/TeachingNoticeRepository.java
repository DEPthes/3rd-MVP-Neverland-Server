package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.TeachingNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeachingNoticeRepository extends JpaRepository<TeachingNotice, Long> {
    @Query("SELECT n FROM TeachingNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<TeachingNotice> searchByTitle(@Param("keyword") String keyword);

    Page<TeachingNotice> findAllByOrderByImportantDescPubDateDesc(Pageable pageable);
}
