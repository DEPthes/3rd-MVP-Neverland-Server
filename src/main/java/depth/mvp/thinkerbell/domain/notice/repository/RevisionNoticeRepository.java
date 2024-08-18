package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.RevisionNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RevisionNoticeRepository extends JpaRepository<RevisionNotice, Long> {
    @Query("SELECT n FROM RevisionNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<RevisionNotice> searchByTitle(@Param("keyword") String keyword);

    RevisionNotice findOneById(Long noticeID);
}
