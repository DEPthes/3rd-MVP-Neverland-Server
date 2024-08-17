package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.AcademicNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicNotice;
import depth.mvp.thinkerbell.domain.notice.repository.AcademicNoticeRepository;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicNoticeService {
    private final BookmarkService bookmarkService;
    private final AcademicNoticeRepository academicNoticeRepository;

    public AcademicNoticeService(BookmarkService bookmarkService, AcademicNoticeRepository academicNoticeRepository) {
        this.bookmarkService = bookmarkService;
        this.academicNoticeRepository = academicNoticeRepository;
    }

    public List<AcademicNoticeDTO> getAllAcademicNotices(boolean important, String ssaid) throws NotFoundException {
        List<AcademicNotice> notices = academicNoticeRepository.findByImportant(important);
        if (notices == null || notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        return notices.stream().map(notice -> {
            boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

            return AcademicNoticeDTO.builder()
                    .id(notice.getId())
                    .pubDate(notice.getPubDate())
                    .title(notice.getTitle())
                    .url(notice.getUrl())
                    .marked(isMarked)
                    .important(notice.isImportant())
                    .build();
        }).collect(Collectors.toList());
    }

}
