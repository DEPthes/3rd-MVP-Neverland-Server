package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.EventNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.ScholarshipNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import depth.mvp.thinkerbell.domain.notice.entity.ScholarshipNotice;
import depth.mvp.thinkerbell.domain.notice.repository.ScholarshipNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScholarshipNoticeService {
    private final BookmarkService bookmarkService;
    private final ScholarshipNoticeRepository scholarshipNoticeRepository;

    public ScholarshipNoticeService(BookmarkService bookmarkService, ScholarshipNoticeRepository scholarshipNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.scholarshipNoticeRepository = scholarshipNoticeRepository;
    }

    public PaginationDTO<ScholarshipNoticeDTO> getAllScholarshipNotices(int page, int size, String ssaid) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScholarshipNotice> resultPage = scholarshipNoticeRepository.findAll(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

        List<ScholarshipNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return ScholarshipNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                })
                .collect(Collectors.toList());
        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }

}
