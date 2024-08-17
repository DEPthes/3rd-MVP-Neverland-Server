package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.JobTrainingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.JobTrainingNotice;
import depth.mvp.thinkerbell.domain.notice.repository.JobTrainingNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTrainingNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private JobTrainingNoticeRepository jobTrainingNoticeRepository;

    public JobTrainingNoticeService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    public PaginationDTO<JobTrainingNoticeDTO> getJobTrainingNotices(int page, int size, String ssaid) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobTrainingNotice> resultPage = jobTrainingNoticeRepository.findAll(pageable);

        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));

//        List<JobTrainingNoticeDTO> dtoList = resultPage.stream()
//                .map(JobTrainingNoticeDTO::fromEntity)
//                .collect(Collectors.toList());
        List<JobTrainingNoticeDTO> dtoList = resultPage.stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return JobTrainingNoticeDTO.builder()
                            .company(notice.getCompany())
                            .year(notice.getYear())
                            .semester(notice.getSemester())
                            .period(notice.getPeriod())
                            .major(notice.getMajor())
                            .recrutingNum(notice.getRecrutingNum())
                            .deadline(notice.getDeadline())
                            .jobName(notice.getJobName())
                            .marked(isMarked)
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
