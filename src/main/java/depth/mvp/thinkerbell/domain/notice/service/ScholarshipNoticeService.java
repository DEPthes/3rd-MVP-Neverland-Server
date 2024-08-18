package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.ScholarshipNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.ScholarshipNotice;
import depth.mvp.thinkerbell.domain.notice.repository.ScholarshipNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
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

    public List<ScholarshipNoticeDTO> getAllScholarshipNotices(String ssaid) throws NotFoundException {
        List<ScholarshipNotice> notices = scholarshipNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return ScholarshipNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
