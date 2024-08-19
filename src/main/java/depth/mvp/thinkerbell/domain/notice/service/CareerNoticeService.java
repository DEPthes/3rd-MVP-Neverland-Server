package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import depth.mvp.thinkerbell.domain.notice.entity.CareerNotice;
import depth.mvp.thinkerbell.domain.notice.entity.EventNotice;
import depth.mvp.thinkerbell.domain.notice.repository.CareerNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerNoticeService {
    private final BookmarkService bookmarkService;
    private final CareerNoticeRepository careerNoticeRepository;

    public CareerNoticeService(BookmarkService bookmarkService, CareerNoticeRepository careerNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.careerNoticeRepository = careerNoticeRepository;
    }

    public PaginationDTO<CareerNoticeDTO> getAllCareerNotices(int page, int size, String ssaid) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<CareerNotice> resultPage = careerNoticeRepository.findAll(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

        List<CareerNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return CareerNoticeDTO.builder()
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
