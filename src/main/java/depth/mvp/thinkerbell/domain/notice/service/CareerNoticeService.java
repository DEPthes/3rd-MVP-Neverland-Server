package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.CareerNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.CareerNotice;
import depth.mvp.thinkerbell.domain.notice.repository.CareerNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerNoticeService {
    private final CareerNoticeRepository careerNoticeRepository;

    public CareerNoticeService(CareerNoticeRepository careerNoticeRepository) {
        this.careerNoticeRepository = careerNoticeRepository;
    }

    public List<CareerNoticeDTO> getAllCareerNotices() throws NotFoundException {
        List<CareerNotice> notices = careerNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new CareerNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
