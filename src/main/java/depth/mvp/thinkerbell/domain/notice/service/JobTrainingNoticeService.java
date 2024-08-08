package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.JobTrainingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.JobTrainingNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTrainingNoticeService {

    @Autowired
    private JobTrainingNoticeRepository jobTrainingNoticeRepository;

    public List<JobTrainingNoticeDTO> getAllJobTrainingNotices() {
        return jobTrainingNoticeRepository.findAll().stream().map(notice -> new JobTrainingNoticeDTO(
                notice.getCompany(), notice.getYear(), notice.getSemester(), notice.getPeriod(), notice.getMajor(), notice.getRecrutingNum(), notice.getDeadline(), notice.getJobName()
        )).collect(Collectors.toList());
    }
}
