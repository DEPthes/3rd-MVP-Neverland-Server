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

    // 모든 중요 공지사항을 가져오는 메서드
    List<TeachingNotice> findAllByImportantTrueOrderByPubDateDesc();

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    Page<TeachingNotice> findAllByImportantFalseOrderByPubDateDesc(Pageable pageable);

    TeachingNotice findOneById(Long noticeID);
}
