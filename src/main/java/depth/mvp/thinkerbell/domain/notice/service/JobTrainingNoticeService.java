package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.JobTrainingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.JobTrainingNotice;
import depth.mvp.thinkerbell.domain.notice.repository.JobTrainingNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTrainingNoticeService {

    @Autowired
    private JobTrainingNoticeRepository jobTrainingNoticeRepository;

    public PaginationDTO<JobTrainingNoticeDTO> getJobTrainingNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobTrainingNotice> resultPage = jobTrainingNoticeRepository.findAll(pageable);

        List<JobTrainingNoticeDTO> dtoList = resultPage.stream()
                .map(JobTrainingNoticeDTO::fromEntity)
                .collect(Collectors.toList());

        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }
}
