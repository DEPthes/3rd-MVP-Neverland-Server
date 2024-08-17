package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.TeachingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.LibraryNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private LibraryNoticeRepository libraryNoticeRepository;

    public LibraryNoticeService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

//    public List<LibraryNoticeDTO> getAllLibraryNotices() {
//        return libraryNoticeRepository.findAll().stream().map(notice -> new LibraryNoticeDTO(
//                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(), notice.getCampus()
//        )).collect(Collectors.toList());
//    }

    public PaginationDTO<LibraryNoticeDTO> getImportantNotices(int page, int size, String ssaid) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LibraryNotice> resultPage = libraryNoticeRepository.findAllByOrderByImportantDescPubDateDesc(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

//        List<LibraryNoticeDTO> dtoList = resultPage.stream()
//                .map(LibraryNoticeDTO::fromEntity)  // DTO 클래스 내의 메서드 호출
//                .collect(Collectors.toList());
        List<LibraryNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return LibraryNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .campus(notice.getCampus())
                            .build();
                })
                // DTO 클래스 내의 메서드 호출
                .collect(Collectors.toList());
        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }
}
