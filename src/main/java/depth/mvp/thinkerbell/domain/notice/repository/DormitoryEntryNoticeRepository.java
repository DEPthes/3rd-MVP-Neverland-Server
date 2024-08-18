package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DormitoryEntryNoticeRepository extends JpaRepository<DormitoryEntryNotice, Long> {
    @Query("SELECT n FROM DormitoryEntryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<DormitoryEntryNotice> searchByTitle(@Param("keyword") String keyword);

    // 모든 중요 공지사항을 가져오는 메서드
    List<DormitoryEntryNotice> findAllByImportantTrueOrderByPubDateDesc();

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    Page<DormitoryEntryNotice> findAllByImportantFalseOrderByPubDateDesc(Pageable pageable);

    DormitoryEntryNotice findOneById(Long noticeID);
}
