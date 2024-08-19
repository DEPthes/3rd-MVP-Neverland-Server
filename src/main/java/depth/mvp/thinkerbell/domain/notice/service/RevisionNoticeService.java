package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.CareerNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.EventNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.RevisionNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.CareerNotice;
import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import depth.mvp.thinkerbell.domain.notice.entity.RevisionNotice;
import depth.mvp.thinkerbell.domain.notice.repository.RevisionNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionNoticeService {
    private final BookmarkService bookmarkService;
    private final RevisionNoticeRepository revisionNoticeRepository;

    public RevisionNoticeService(BookmarkService bookmarkService, RevisionNoticeRepository revisionNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.revisionNoticeRepository = revisionNoticeRepository;
    }

    public PaginationDTO<RevisionNoticeDTO> getAllRevisionNotices(int page, int size, String ssaid) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<RevisionNotice> resultPage = revisionNoticeRepository.findAll(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

        List<RevisionNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return RevisionNoticeDTO.builder()
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
