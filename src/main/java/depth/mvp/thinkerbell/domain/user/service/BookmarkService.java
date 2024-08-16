package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.user.entity.Bookmark;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public List<Long> getBookmark(String ssaid, String category) {
        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return bookmarkRepository.findByUserAndCategory(user, category)
                .stream()
                .map(Bookmark::getNoticeID).toList();
    }
    public Bookmark saveBookmark(String category, Long noticeId, String ssaid) {

        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        if (bookmarkRepository.findByCategoryAndNoticeIDAndUser(category, noticeId, user) != null){
            throw new NotFoundException("이미 즐겨찾기한 공지입니다.");
        }else {
            return bookmarkRepository.save(Bookmark.builder()
                    .category(category)
                    .noticeID(noticeId)
                    .user(user)
                    .build());
        }
    }
    public void deleteBookmark(String category, Long noticeId, String ssaid) {

        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        Bookmark bookmark = bookmarkRepository.findByCategoryAndNoticeIDAndUser(category, noticeId, user);
        if (bookmark == null) {
            new NotFoundException("즐겨찾기 내역을 찾을 수 없습니다.");
        }
        bookmarkRepository.deleteById(bookmark.getId());
    }
}
