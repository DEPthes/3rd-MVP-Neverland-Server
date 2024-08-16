package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.RevisionNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.RevisionNotice;
import depth.mvp.thinkerbell.domain.notice.repository.RevisionNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionNoticeService {
    private final BookmarkService bookmarkService;
    private final RevisionNoticeRepository revisionNoticeRepository;

    public RevisionNoticeService(BookmarkService bookmarkService, RevisionNoticeRepository revisionNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.revisionNoticeRepository = revisionNoticeRepository;
    }

    public List<RevisionNoticeDTO> getAllRevisionNotices(String ssaid) throws NotFoundException {
        List<RevisionNotice> notices = revisionNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return RevisionNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
