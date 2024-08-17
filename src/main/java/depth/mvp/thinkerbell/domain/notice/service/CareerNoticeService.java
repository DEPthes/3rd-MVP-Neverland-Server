package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.CareerNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.CareerNotice;
import depth.mvp.thinkerbell.domain.notice.repository.CareerNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
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

    public List<CareerNoticeDTO> getAllCareerNotices(String ssaid) throws NotFoundException {
        List<CareerNotice> notices = careerNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return CareerNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
