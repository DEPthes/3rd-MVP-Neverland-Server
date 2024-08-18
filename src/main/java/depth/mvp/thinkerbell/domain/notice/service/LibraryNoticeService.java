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

    public PaginationDTO<LibraryNoticeDTO> getImportantNotices(int page, int size) {
        if (page == 0) {
            // 첫 번째 페이지
            // 중요 공지사항 모두 가져오기
            List<LibraryNotice> importantNotices = libraryNoticeRepository.findAllByImportantTrueOrderByPubDateDesc();

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(0, 10);
            Page<LibraryNotice> latestNoticesPage = libraryNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(latestPageable);

            // DTO 변환
            List<LibraryNoticeDTO> dtoList = importantNotices.stream()
                    .map(LibraryNoticeDTO::fromEntity)
                    .collect(Collectors.toList());

            dtoList.addAll(latestNoticesPage.stream()
                    .map(LibraryNoticeDTO::fromEntity)
                    .collect(Collectors.toList()));

            return new PaginationDTO<>(
                    dtoList,
                    0, // 첫 페이지 번호
                    dtoList.size(), // 가져온 항목의 개수
                    importantNotices.size() + latestNoticesPage.getTotalElements() // 총 항목 수
            );
        } else {
            // 첫 페이지가 아닌 경우 최신 공지사항만 처리
            Pageable pageable = PageRequest.of(page - 1, size);  // 첫 페이지는 이미 처리했으므로 page - 1
            Page<LibraryNotice> resultPage = libraryNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(pageable);

            List<LibraryNoticeDTO> dtoList = resultPage.stream()
                    .map(LibraryNoticeDTO::fromEntity)
                    .collect(Collectors.toList());

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber() + 1, // 페이지 번호를 1 추가하여 반환
                    resultPage.getSize(),
                    resultPage.getTotalElements()
            );
        }
    }
}
