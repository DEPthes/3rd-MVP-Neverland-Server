package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DormitoryNoticeRepository extends JpaRepository<DormitoryNotice, Long> {
    @Query("SELECT n FROM DormitoryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<DormitoryNotice> searchByTitle(@Param("keyword") String keyword);

    // 중요 공지사항을 모두 가져오는 메서드
    List<DormitoryNotice> findAllByImportantTrueAndCampusOrderByPubDateDesc(String campus);

    // 중요하지 않은 공지사항을 페이지네이션하여 가져오는 메서드
    Page<DormitoryNotice> findAllByImportantFalseAndCampusOrderByPubDateDesc(Pageable pageable, String campus);

    DormitoryNotice findOneById(Long id);
}