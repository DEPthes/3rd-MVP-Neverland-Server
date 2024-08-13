package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.AllNoticesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AllNoticeViewRepository extends JpaRepository<AllNoticesView, Long> {
    @Query(value = "SELECT * FROM All_Notices_View e WHERE REPLACE(e.title, ' ', '') LIKE %:keyword%", nativeQuery = true)
    List<AllNoticesView> findByTitleContainingKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM All_Notices_View WHERE table_name = :category AND id > :maxId", nativeQuery = true)
    List<AllNoticesView> findNewNoticesByCategory(@Param("category") String category, @Param("maxId") Long maxId);

    @Query(value = "SELECT MAX(id) FROM All_Notices_View WHERE table_name = :category", nativeQuery = true)
    Long findMaxIdByCategory(@Param("category") String category);
}
