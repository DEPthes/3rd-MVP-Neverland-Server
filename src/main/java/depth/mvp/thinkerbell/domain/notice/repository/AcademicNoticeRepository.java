package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import depth.mvp.thinkerbell.domain.notice.entity.NormalNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicNoticeRepository extends JpaRepository<AcademicNotice, Long> {
    @Query("SELECT n FROM AcademicNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<AcademicNotice> searchByTitle(@Param("keyword") String keyword);
    AcademicNotice findOneById(Long noticeID);
    List<AcademicNotice> findAllByImportantTrueOrderByPubDateDesc();
    Page<AcademicNotice> findAllByImportantFalseOrderByPubDateDesc(Pageable latestPageable);
    List<AcademicNotice> findTop3ByOrderByPubDateDesc();

}
