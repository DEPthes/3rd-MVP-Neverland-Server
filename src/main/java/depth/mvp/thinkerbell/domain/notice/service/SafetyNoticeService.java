package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.SafetyNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.SafetyNotice;
import depth.mvp.thinkerbell.domain.notice.repository.SafetyNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SafetyNoticeService {
    private final SafetyNoticeRepository safetyNoticeRepository;

    public SafetyNoticeService(SafetyNoticeRepository safetyNoticeRepository) {
        this.safetyNoticeRepository = safetyNoticeRepository;
    }

    public List<SafetyNoticeDTO> getAllSafetyNotices() throws NotFoundException {
        List<SafetyNotice> notices = safetyNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new SafetyNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
