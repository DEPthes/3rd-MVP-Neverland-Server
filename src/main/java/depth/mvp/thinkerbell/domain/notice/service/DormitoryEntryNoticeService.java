package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryEntryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryEntryNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryEntryNoticeService {

    @Autowired
    private DormitoryEntryNoticeRepository dormitoryEntryNoticeRepository;

    public List<DormitoryEntryNoticeDTO> getAllEntryNotices() {
        return dormitoryEntryNoticeRepository.findAll().stream().map(notice -> new DormitoryEntryNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(),
                notice.isImportant(), notice.getCategory()
        )).collect(Collectors.toList());

    }
}
