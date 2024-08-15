package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryNoticeRepository extends JpaRepository<LibraryNotice, Long> {
    @Query("SELECT n FROM LibraryNotice n WHERE n.title LIKE CONCAT('%', :keyword, '%')")
    List<LibraryNotice> searchByTitle(@Param("keyword") String keyword);

    Page<LibraryNotice> findAllByOrderByIsImportantDescPubDateDesc(Pageable pageable);

}
