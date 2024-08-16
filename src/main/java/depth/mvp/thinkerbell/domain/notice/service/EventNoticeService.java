package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.EventNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import depth.mvp.thinkerbell.domain.notice.repository.EventNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventNoticeService {
    private final BookmarkService bookmarkService;
    private final EventNoticeRepository eventNoticeRepository;

    public EventNoticeService(BookmarkService bookmarkService, EventNoticeRepository eventNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.eventNoticeRepository = eventNoticeRepository;
    }

    public List<EventNoticeDTO> getAllEventNotices(String ssaid) throws NotFoundException {
        List<EventNotice> notices = eventNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return EventNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
