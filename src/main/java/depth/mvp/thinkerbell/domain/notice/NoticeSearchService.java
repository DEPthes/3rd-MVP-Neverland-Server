package depth.mvp.thinkerbell.domain.notice;

import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeSearchService {
    private final DormitoryEntryNoticeRepository dormitoryEntryNoticeRepository;
    private final DormitoryNoticeRepository dormitoryNoticeRepository;
    private final LibraryNoticeRepository libraryNoticeRepository;
    private final TeachingNoticeRepository teachingNoticeRepository;
    private final JobTrainingNoticeRepository jobTrainingNoticeRepository;

    public Map<String, List<?>> searchNotices(String keyword) {
        Map<String, List<?>> result = new HashMap<>();

        // DormitoryEntryNotice 검색 및 DTO 변환
        List<DormitoryEntryNoticeDTO> dormitoryEntryNotices = dormitoryEntryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new DormitoryEntryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null,
                        null
                )).collect(Collectors.toList());

        if (!dormitoryEntryNotices.isEmpty()) {
            result.put("dormitoryEntryNotices", dormitoryEntryNotices);
        }

        // DormitoryNotice 검색 및 DTO 변환
        List<DormitoryNoticeDTO> dormitoryNotices = dormitoryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new DormitoryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null,
                        null
                )).collect(Collectors.toList());

        if (!dormitoryNotices.isEmpty()) {
            result.put("dormitoryNotices", dormitoryNotices);
        }

        // LibraryNotice 검색 및 DTO 변환
        List<LibraryNoticeDTO> libraryNotices = libraryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new LibraryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null,
                        null
                )).collect(Collectors.toList());

        if (!libraryNotices.isEmpty()) {
            result.put("libraryNotices", libraryNotices);
        }

       //  TeachingNotice 검색 및 DTO 변환
        List<TeachingNoticeDTO> teachingNotices = teachingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new TeachingNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null
                )).collect(Collectors.toList());

        if (!teachingNotices.isEmpty()) {
            result.put("teachingNotices", teachingNotices);
        }

        // JobTraingingNotice 검색 및 DTO 변환
        List<JobTrainingNoticeDTO> jobTrainingNotices = jobTrainingNoticeRepository.searchByTitleOrMajor(keyword)
                .stream()
                .map(notice -> new JobTrainingNoticeDTO(
                        notice.getCompany(),
                        notice.getYear(),
                        notice.getSemester(),
                        notice.getPeriod(),
                        notice.getMajor(),
                        notice.getRecrutingNum(),
                        notice.getDeadline(),
                        notice.getJobName()
                )).collect(Collectors.toList());

        if (!jobTrainingNotices.isEmpty()) {
            result.put("jobTrainingNotices", jobTrainingNotices);
        }

        return result;
    }
}