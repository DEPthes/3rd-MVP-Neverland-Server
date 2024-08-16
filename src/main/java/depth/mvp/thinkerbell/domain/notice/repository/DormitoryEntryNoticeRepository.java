package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DormitoryEntryNoticeRepository extends JpaRepository<DormitoryEntryNotice, Long> {
    @Query("SELECT n FROM DormitoryEntryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<DormitoryEntryNotice> searchByTitle(@Param("keyword") String keyword);

    Page<DormitoryEntryNotice> findAllByOrderByImportantDescPubDateDesc(Pageable pageable);
}
