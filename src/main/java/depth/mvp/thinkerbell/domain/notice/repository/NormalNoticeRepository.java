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
public interface NormalNoticeRepository  extends JpaRepository<NormalNotice, Long> {
    @Query("SELECT n FROM NormalNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<NormalNotice> searchByTitle(@Param("keyword") String keyword);

    NormalNotice findOneById(Long noticeID);

    List<NormalNotice> findAllByImportantTrueOrderByPubDateDesc();
    Page<NormalNotice> findAllByImportantFalseOrderByPubDateDesc(Pageable latestPageable);
    List<NormalNotice> findTop3ByOrderByPubDateDesc();

}
