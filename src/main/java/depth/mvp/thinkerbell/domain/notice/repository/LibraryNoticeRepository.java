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

    // 중요 공지사항을 가져오는 메서드
    List<LibraryNotice> findAllByImportantTrueAndCampusOrderByPubDateDesc(String campus);

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    Page<LibraryNotice> findAllByImportantFalseAndCampusOrderByPubDateDesc(Pageable pageable, String campus);

    LibraryNotice findOneById(Long noticeID);
}
