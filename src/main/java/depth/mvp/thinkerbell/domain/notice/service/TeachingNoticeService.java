package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.TeachingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.TeachingNotice;
import depth.mvp.thinkerbell.domain.notice.repository.TeachingNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private TeachingNoticeRepository teachingNoticeRepository;

    public TeachingNoticeService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    public PaginationDTO<TeachingNoticeDTO> getImportantNotices(int page, int size, String ssaid) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TeachingNotice> resultPage = teachingNoticeRepository.findAllByOrderByImportantDescPubDateDesc(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

//        List<TeachingNoticeDTO> dtoList = resultPage.stream()
//                .map(TeachingNoticeDTO::fromEntity)  // DTO 클래스 내의 메서드 호출
//                .collect(Collectors.toList());
        List<TeachingNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return TeachingNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
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
