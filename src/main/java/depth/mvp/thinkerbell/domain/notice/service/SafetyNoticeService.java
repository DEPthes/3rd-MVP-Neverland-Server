package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.SafetyNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.SafetyNotice;
import depth.mvp.thinkerbell.domain.notice.repository.SafetyNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SafetyNoticeService {
    private final BookmarkService bookmarkService;
    private final SafetyNoticeRepository safetyNoticeRepository;

    public SafetyNoticeService(BookmarkService bookmarkService, SafetyNoticeRepository safetyNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.safetyNoticeRepository = safetyNoticeRepository;
    }

    public List<SafetyNoticeDTO> getAllSafetyNotices(String ssaid) throws NotFoundException {
        List<SafetyNotice> notices = safetyNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return SafetyNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
