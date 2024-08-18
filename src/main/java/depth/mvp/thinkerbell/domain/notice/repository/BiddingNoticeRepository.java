package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.BiddingNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BiddingNoticeRepository extends JpaRepository<BiddingNotice, Long> {
    @Query("SELECT n FROM BiddingNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<BiddingNotice> searchByTitle(@Param("keyword") String keyword);

    BiddingNotice findOneById(Long noticeID);
}
