package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.SafetyNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SafetyNoticeRepository extends JpaRepository<SafetyNotice, Long> {
    @Query("SELECT n FROM SafetyNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<SafetyNotice> searchByTitle(@Param("keyword") String keyword);

    SafetyNotice findOneById(Long noticeID);
}
