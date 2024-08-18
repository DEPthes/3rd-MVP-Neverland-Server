package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventNoticeRepository extends JpaRepository<EventNotice, Long> {
    @Query("SELECT n FROM EventNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<EventNotice> searchByTitle(@Param("keyword") String keyword);

    EventNotice findOneById(Long noticeID);
}
