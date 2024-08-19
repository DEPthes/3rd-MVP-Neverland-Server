package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryEntryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.DormitoryEntryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryEntryNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryEntryNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private DormitoryEntryNoticeRepository dormitoryEntryNoticeRepository;

    public DormitoryEntryNoticeService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    public PaginationDTO<DormitoryEntryNoticeDTO> getImportantNotices(int page, int size, String ssaid, String campus) {
        // USER가 북마크한 내역(id리스트) 가져오기
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        if (page == 0) {
            // 첫 번째 페이지: 중요 공지사항 모두 가져오기
            List<DormitoryEntryNotice> importantNotices = dormitoryEntryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campus);

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(page, size);
            Page<DormitoryEntryNotice> latestNoticesPage = dormitoryEntryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(latestPageable, campus);

            // DTO 변환
            List<DormitoryEntryNoticeDTO> dtoList = importantNotices.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return DormitoryEntryNoticeDTO.builder()
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

                        return DormitoryEntryNoticeDTO.builder()
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
                    page, // 첫 페이지 번호
                    dtoList.size(), // 가져온 항목의 개수
                    importantNotices.size() + latestNoticesPage.getTotalElements() // 총 항목 수
            );
        } else {
            // 이후 페이지: 최신 공지사항만 페이지네이션
            Pageable pageable = PageRequest.of(page, size);
            Page<DormitoryEntryNotice> resultPage = dormitoryEntryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(pageable, campus);

            List<DormitoryEntryNoticeDTO> dtoList = resultPage.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return DormitoryEntryNoticeDTO.builder()
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

            int importantNoticesSize = dormitoryEntryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campus).size();

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber(),
                    resultPage.getSize(),
                    importantNoticesSize + resultPage.getTotalElements()
            );
        }
    }
}
