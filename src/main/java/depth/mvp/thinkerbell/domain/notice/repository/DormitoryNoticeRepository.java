package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DormitoryNoticeRepository extends JpaRepository<DormitoryNotice, Long> {
    @Query("SELECT n FROM DormitoryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<DormitoryNotice> searchByTitle(@Param("keyword") String keyword);
}