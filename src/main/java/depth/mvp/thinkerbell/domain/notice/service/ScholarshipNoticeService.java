package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.ScholarshipNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.ScholarshipNotice;
import depth.mvp.thinkerbell.domain.notice.repository.ScholarshipNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScholarshipNoticeService {
    private final ScholarshipNoticeRepository scholarshipNoticeRepository;

    public ScholarshipNoticeService(ScholarshipNoticeRepository scholarshipNoticeRepository) {
        this.scholarshipNoticeRepository = scholarshipNoticeRepository;
    }

    public List<ScholarshipNoticeDTO> getAllScholarshipNotices() throws NotFoundException {
        List<ScholarshipNotice> notices = scholarshipNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new ScholarshipNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
