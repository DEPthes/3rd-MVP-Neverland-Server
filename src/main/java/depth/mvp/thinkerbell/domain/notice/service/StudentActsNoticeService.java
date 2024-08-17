package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.StudentActsNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.StudentActsNotice;
import depth.mvp.thinkerbell.domain.notice.repository.StudentActsNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentActsNoticeService {
    private final BookmarkService bookmarkService;
    private final StudentActsNoticeRepository studentActsNoticeRepository;

    public StudentActsNoticeService(BookmarkService bookmarkService, StudentActsNoticeRepository studentActsNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.studentActsNoticeRepository = studentActsNoticeRepository;
    }

    public List<StudentActsNoticeDTO> getAllStudentActsNotices(String ssaid) throws NotFoundException {
        List<StudentActsNotice> notices = studentActsNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return StudentActsNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .build();
        }).collect(Collectors.toList());
    }

}
