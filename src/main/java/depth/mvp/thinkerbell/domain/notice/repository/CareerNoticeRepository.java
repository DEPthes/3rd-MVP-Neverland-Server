package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.CareerNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerNoticeRepository extends JpaRepository<CareerNotice, Long> {
    @Query("SELECT n FROM CareerNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<CareerNotice> searchByTitle(@Param("keyword") String keyword);

    CareerNotice findOneById(Long noticeID);
}
