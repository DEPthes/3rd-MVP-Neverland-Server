package depth.mvp.thinkerbell.domain.user.repository;

import depth.mvp.thinkerbell.domain.user.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    boolean existsByUserIdAndKeywordAndIsViewedFalse(Long userId, String keyword);

    List<Alarm> findALLByUserIdAndKeyword (Long userId, String keyword);

}
