package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private DormitoryNoticeRepository dormitoryNoticeRepository;

    public DormitoryNoticeService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    public PaginationDTO<DormitoryNoticeDTO> getImportantNotices(int page, int size, String ssaid, String campus) {
        // USER가 북마크한 내역(id리스트) 가져오기
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        if (page == 0) {
            // 첫 페이지: 중요 공지사항 모두 가져오기
            List<DormitoryNotice> importantNotices = dormitoryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campus);

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(page, size);
            Page<DormitoryNotice> latestNoticesPage = dormitoryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(latestPageable, campus);

            // DTO 변환
            List<DormitoryNoticeDTO> dtoList = importantNotices.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return DormitoryNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .campus(notice.getCampus())
                                .important(notice.isImportant())
                                .build();
                    })
                    .collect(Collectors.toList());

            dtoList.addAll(latestNoticesPage.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return DormitoryNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .campus(notice.getCampus())
                                .important(notice.isImportant())
                                .build();
                    }).toList());

            return new PaginationDTO<>(
                    dtoList,
                    page, // 첫 페이지 번호
                    dtoList.size(), // 가져온 항목의 개수
                    importantNotices.size() + latestNoticesPage.getTotalElements() // 총 항목 수
            );
        } else {
            // 그 이후 페이지: 최신 공지사항만 페이지네이션
            Pageable pageable = PageRequest.of(page, size);
            Page<DormitoryNotice> resultPage = dormitoryNoticeRepository.findAllByImportantFalseAndCampusOrderByPubDateDesc(pageable, campus);

            List<DormitoryNoticeDTO> dtoList = resultPage.stream()
                    .map(notice -> {
                        boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                        return DormitoryNoticeDTO.builder()
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

            int importantNoticesSize = dormitoryNoticeRepository.findAllByImportantTrueAndCampusOrderByPubDateDesc(campus).size();

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber(),
                    resultPage.getSize(),
                    importantNoticesSize + resultPage.getTotalElements()
            );
        }
    }
}
