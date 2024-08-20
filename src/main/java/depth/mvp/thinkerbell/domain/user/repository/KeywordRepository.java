package depth.mvp.thinkerbell.domain.user.repository;

import depth.mvp.thinkerbell.domain.user.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Keyword k WHERE k.keyword = :keyword AND k.user.id = :userId")
    void deleteByKeywordAndUserId(@Param("keyword") String keyword, @Param("userId") Long userId);

    boolean existsByKeywordAndUserId(String keyword, Long userId);

    List<Keyword> findAllByUserId(Long userId);

    long countByUserId(Long userId);
}
