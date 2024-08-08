package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.TeachingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.TeachingNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingNoticeService {

    @Autowired
    private TeachingNoticeRepository teachingNoticeRepository;

    public List<TeachingNoticeDTO> getAllTeachingNotices() {
        return teachingNoticeRepository.findAll().stream().map(notice -> new TeachingNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.isImportant()
        )).collect(Collectors.toList());
    }
}
