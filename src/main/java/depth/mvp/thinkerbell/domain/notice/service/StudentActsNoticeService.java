package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.StudentActsNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.StudentActsNotice;
import depth.mvp.thinkerbell.domain.notice.repository.StudentActsNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentActsNoticeService {
    private final StudentActsNoticeRepository studentActsNoticeRepository;

    public StudentActsNoticeService(StudentActsNoticeRepository studentActsNoticeRepository) {
        this.studentActsNoticeRepository = studentActsNoticeRepository;
    }

    public List<StudentActsNoticeDTO> getAllStudentActsNotices() throws NotFoundException {
        List<StudentActsNotice> notices = studentActsNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new StudentActsNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
