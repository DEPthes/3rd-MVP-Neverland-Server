package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.LibraryNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryNoticeService {

    @Autowired
    private LibraryNoticeRepository libraryNoticeRepository;

//    public List<LibraryNoticeDTO> getAllLibraryNotices() {
//        return libraryNoticeRepository.findAll().stream().map(notice -> new LibraryNoticeDTO(
//                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.getCampus()
//        )).collect(Collectors.toList());
//    }

    public PaginationDTO<LibraryNoticeDTO> getImportantNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LibraryNotice> resultPage = libraryNoticeRepository.findAllByOrderByImportantDescPubDateDesc(pageable);

        List<LibraryNoticeDTO> dtoList = resultPage.stream()
                .map(LibraryNoticeDTO::fromEntity)  // DTO 클래스 내의 메서드 호출
                .collect(Collectors.toList());

        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }
}
