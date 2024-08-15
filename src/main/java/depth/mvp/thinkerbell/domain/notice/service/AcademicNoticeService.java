package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.AcademicNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import depth.mvp.thinkerbell.domain.notice.repository.AcademicNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicNoticeService {
    private final AcademicNoticeRepository academicNoticeRepository;

    public AcademicNoticeService(AcademicNoticeRepository academicNoticeRepository) {
        this.academicNoticeRepository = academicNoticeRepository;
    }

    public List<AcademicNoticeDTO> getAllAcademicNotices(boolean isImportant) throws NotFoundException {
        List<AcademicNotice> notices = academicNoticeRepository.findByIsImportant(isImportant);
        if (notices == null || notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new AcademicNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.isImportant()
        )).collect(Collectors.toList());
    }

}
