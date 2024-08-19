package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.AcademicNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.AcademicNoticeRepository;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicNoticeService {
    private final BookmarkService bookmarkService;
    private final AcademicNoticeRepository academicNoticeRepository;

    public AcademicNoticeService(BookmarkService bookmarkService, AcademicNoticeRepository academicNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.academicNoticeRepository = academicNoticeRepository;
    }

    public PaginationDTO<AcademicNoticeDTO> getAllAcademicNotices(int page, int size, String ssaid) {
        // USER가 북마크한 내역(id리스트) 가져오기
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        if (page == 0) {
            // 첫 번째 페이지
            // 중요 공지사항 모두 가져오기
            List<AcademicNotice> importantNotices = academicNoticeRepository.findAllByImportantTrueOrderByPubDateDesc();

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(page, size);
            Page<AcademicNotice> latestNoticesPage = academicNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(latestPageable);

            // DTO 변환
            List<AcademicNoticeDTO> dtoList = importantNotices.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return AcademicNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .important(notice.isImportant())
                                .build();
                    })
                    .collect(Collectors.toList());

            dtoList.addAll(latestNoticesPage.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return AcademicNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .important(notice.isImportant())
                                .build();
                    }).toList());

            return new PaginationDTO<>(
                    dtoList,
                    page,
                    dtoList.size(), // 가져온 항목의 개수
                    importantNotices.size() + latestNoticesPage.getTotalElements() // 총 항목 수
            );
        } else {
            // 첫 페이지가 아닌 경우 최신 공지사항만 처리
            Pageable pageable = PageRequest.of(page, size);
            Page<AcademicNotice> resultPage = academicNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(pageable);

            List<AcademicNoticeDTO> dtoList = resultPage.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return AcademicNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .important(notice.isImportant())
                                .build();
                    })
                    .collect(Collectors.toList());

            int importantNoticesSize = academicNoticeRepository.findAllByImportantTrueOrderByPubDateDesc().size();

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber(),
                    resultPage.getSize(),
                    importantNoticesSize + resultPage.getTotalElements()
            );
        }
    }
}
