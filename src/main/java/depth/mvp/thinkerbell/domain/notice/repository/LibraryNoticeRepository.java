package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryNoticeRepository extends JpaRepository<LibraryNotice, Long> {
    @Query("SELECT n FROM LibraryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<LibraryNotice> searchByTitle(@Param("keyword") String keyword);

    @Query("SELECT d FROM LibraryNotice d WHERE d.important = true AND d.campus IN :campuses ORDER BY d.pubDate DESC")
    List<LibraryNotice> findAllByImportantTrueAndCampusOrderByPubDateDesc(@Param("campuses") List<String> campuses);

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    @Query("SELECT d FROM LibraryNotice d WHERE d.important = false AND d.campus IN :campuses ORDER BY d.pubDate DESC")
    Page<LibraryNotice> findAllByImportantFalseAndCampusOrderByPubDateDesc(Pageable pageable, @Param("campuses") List<String> campuses);

    LibraryNotice findOneById(Long noticeID);
}
