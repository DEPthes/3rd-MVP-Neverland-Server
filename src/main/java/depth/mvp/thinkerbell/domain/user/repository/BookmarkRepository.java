package depth.mvp.thinkerbell.domain.user.repository;

import depth.mvp.thinkerbell.domain.user.entity.Bookmark;
import depth.mvp.thinkerbell.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserAndCategory(User user, String category);
    Bookmark findByCategoryAndNoticeIDAndUser(String category, Long NoticeId, User user);
    boolean existsByCategoryAndNoticeIDAndUser(String category, Long noticeID, User user);
    List<Bookmark> findByUser(User user);
}
