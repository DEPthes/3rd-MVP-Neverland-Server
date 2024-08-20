package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.LibraryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.LibraryNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
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

    public PaginationDTO<LibraryNoticeDTO> getImportantNotices(int page, int size, String ssaid, String campus) {
        // USER가 북마크한 내역(id리스트) 가져오기
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

        List<String> campuses;

        if ("전체".equals(campus)){
            campuses = Arrays.asList("인문", "자연", "공통");
        } else {
            campuses = Collections.singletonList(campus);
        }

        if (page == 0) {
            // 첫 번째 페이지
            // 중요 공지사항 모두 가져오기
            List<LibraryNotice> importantNotices = libraryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campuses);

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(page, size);
            Page<LibraryNotice> latestNoticesPage = libraryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(latestPageable, campuses);

            // DTO 변환
            List<LibraryNoticeDTO> dtoList = importantNotices.stream()
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
                    .collect(Collectors.toList());

            dtoList.addAll(latestNoticesPage.stream()
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
            Page<LibraryNotice> resultPage = libraryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(pageable, campuses);

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
                    .collect(Collectors.toList());

            int importantNoticesSize = libraryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campuses).size();

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber(),
                    resultPage.getSize(),
                    importantNoticesSize + resultPage.getTotalElements()
            );
        }
    }
}
