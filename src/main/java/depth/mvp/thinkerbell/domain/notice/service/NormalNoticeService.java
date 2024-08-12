package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.NormalNotice;
import depth.mvp.thinkerbell.domain.notice.repository.NormalNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NormalNoticeService {
    private final NormalNoticeRepository normalNoticeRepository;

    public NormalNoticeService(NormalNoticeRepository normalNoticeRepository) {
        this.normalNoticeRepository = normalNoticeRepository;
    }

    public List<NormalNoticeDTO> getAllNormalNotices(boolean isImportant) throws NotFoundException {
        List<NormalNotice> notices = normalNoticeRepository.findByIsImportant(isImportant);
        if (notices == null || notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new NormalNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.isImportant()
        )).collect(Collectors.toList());
    }

}
