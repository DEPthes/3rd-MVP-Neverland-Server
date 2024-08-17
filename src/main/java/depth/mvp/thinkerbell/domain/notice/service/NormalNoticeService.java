package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.NormalNotice;
import depth.mvp.thinkerbell.domain.notice.repository.NormalNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NormalNoticeService {
    private final BookmarkService bookmarkService;
    private final NormalNoticeRepository normalNoticeRepository;

    public NormalNoticeService(BookmarkService bookmarkService, NormalNoticeRepository normalNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.normalNoticeRepository = normalNoticeRepository;
    }

    public List<NormalNoticeDTO> getAllNormalNotices(boolean important, String ssaid) throws NotFoundException {
        List<NormalNotice> notices = normalNoticeRepository.findByImportant(important);
        if (notices == null || notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return NormalNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .important(notice.isImportant())
                    .build();
        }).collect(Collectors.toList());
    }

}
