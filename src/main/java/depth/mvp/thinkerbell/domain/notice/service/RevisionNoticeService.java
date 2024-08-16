package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.RevisionNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.RevisionNotice;
import depth.mvp.thinkerbell.domain.notice.repository.RevisionNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionNoticeService {
    private final RevisionNoticeRepository revisionNoticeRepository;

    public RevisionNoticeService(RevisionNoticeRepository revisionNoticeRepository) {
        this.revisionNoticeRepository = revisionNoticeRepository;
    }

    public List<RevisionNoticeDTO> getAllRevisionNotices() throws NotFoundException {
        List<RevisionNotice> notices = revisionNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new RevisionNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
