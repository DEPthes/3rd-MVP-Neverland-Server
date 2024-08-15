package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.EventNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import depth.mvp.thinkerbell.domain.notice.repository.EventNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventNoticeService {
    private final EventNoticeRepository eventNoticeRepository;

    public EventNoticeService(EventNoticeRepository eventNoticeRepository) {
        this.eventNoticeRepository = eventNoticeRepository;
    }

    public List<EventNoticeDTO> getAllEventNotices() throws NotFoundException {
        List<EventNotice> notices = eventNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new EventNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
