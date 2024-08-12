package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.AllNoticesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AllNoticeViewRepository extends JpaRepository<AllNoticesView, Long> {
    @Query(value = "SELECT * FROM All_Notices_View e WHERE REPLACE(e.title, ' ', '') LIKE %:keyword%", nativeQuery = true)
    List<AllNoticesView> findByTitleContainingKeyword(@Param("keyword") String keyword);
}
