package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DormitoryEntryNoticeRepository extends JpaRepository<DormitoryEntryNotice, Long> {
    @Query("SELECT n FROM DormitoryEntryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<DormitoryEntryNotice> searchByTitle(@Param("keyword") String keyword);

    // 중요 공지사항을 모두 가져오는 메서드
    @Query("SELECT d FROM DormitoryEntryNotice d WHERE d.important = true AND d.campus IN :campuses ORDER BY d.pubDate DESC")
    List<DormitoryEntryNotice> findAllByImportantTrueAndCampusOrderByPubDateDesc(@Param("campuses") List<String> campuses);

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    @Query("SELECT d FROM DormitoryEntryNotice d WHERE d.important = false AND d.campus IN :campuses ORDER BY d.pubDate DESC")
    Page<DormitoryEntryNotice> findAllByImportantFalseAndCampusOrderByPubDateDesc(Pageable pageable, @Param("campuses") List<String> campuses);

    DormitoryEntryNotice findOneById(Long noticeID);
}
