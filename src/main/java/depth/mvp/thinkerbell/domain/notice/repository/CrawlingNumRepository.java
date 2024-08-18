package depth.mvp.thinkerbell.domain.notice.repository;

import depth.mvp.thinkerbell.domain.notice.entity.CrawlingNum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrawlingNumRepository extends JpaRepository<CrawlingNum, Long> {
    Optional<CrawlingNum> findByNoticeType(String noticeType);
}
