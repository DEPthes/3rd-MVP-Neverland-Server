package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.BiddingNotice;
import depth.mvp.thinkerbell.domain.notice.repository.BiddingNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BiddingNoticeService {
    private final BookmarkService bookmarkService;
    private final BiddingNoticeRepository biddingNoticeRepository;

    public BiddingNoticeService(BookmarkService bookmarkService, BiddingNoticeRepository biddingNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.biddingNoticeRepository = biddingNoticeRepository;
    }

    public List<BiddingNoticeDTO> getAllBiddingNotices(String ssaid) throws NotFoundException {
        List<BiddingNotice> notices = biddingNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return BiddingNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
