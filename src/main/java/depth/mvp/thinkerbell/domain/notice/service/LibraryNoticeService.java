package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.LibraryNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryNoticeService {

    @Autowired
    private LibraryNoticeRepository libraryNoticeRepository;

    public List<LibraryNoticeDTO> getAllLibraryNotices() {
        return libraryNoticeRepository.findAll().stream().map(notice -> new LibraryNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.getCampus()
        )).collect(Collectors.toList());
    }
}
