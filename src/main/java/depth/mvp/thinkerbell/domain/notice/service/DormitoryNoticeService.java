package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryNoticeService {

    @Autowired
    private DormitoryNoticeRepository dormitoryNoticeRepository;

    public List<DormitoryNoticeDTO> getAllNotices() {
        return dormitoryNoticeRepository.findAll().stream().map(notice -> new DormitoryNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(),
                notice.isImportant(), notice.getCategory()
        )).collect(Collectors.toList());
    }
}
