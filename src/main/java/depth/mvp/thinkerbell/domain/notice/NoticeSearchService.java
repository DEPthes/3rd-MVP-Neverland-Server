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
    private final NormalNoticeRepository normalNoticeRepository;
    private final AcademicNoticeRepository academicNoticeRepository;
    private final EventNoticeRepository eventNoticeRepository;
    private final ScholarshipNoticeRepository scholarshipNoticeRepository;
    private final StudentActsNoticeRepository studentActsNoticeRepository;
    private final BiddingNoticeRepository biddingNoticeRepository;
    private final SafetyNoticeRepository safetyNoticeRepository;
    private final RevisionNoticeRepository revisionNoticeRepository;


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

        // NormalNotice 검색 및 DTO 변환
        List<NormalNoticeDTO> normalNotices = normalNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new NormalNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null
                )).collect(Collectors.toList());

        if (!normalNotices.isEmpty()) {
            result.put("normalNotices", normalNotices);
        }

        // AcademicNotice 검색 및 DTO 변환
        List<AcademicNoticeDTO> academicNotices = academicNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new AcademicNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null
                )).collect(Collectors.toList());

        if (!academicNotices.isEmpty()) {
            result.put("academicNotices", academicNotices);
        }

        // EventNotices 검색 및 DTO 변환
        List<EventNoticeDTO> eventNotices = eventNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new EventNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!eventNotices.isEmpty()) {
            result.put("eventNotices", eventNotices);
        }

        // ScholarshipNotices 검색 및 DTO 변환
        List<ScholarshipNoticeDTO> scholarshipNotices = scholarshipNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new ScholarshipNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!scholarshipNotices.isEmpty()) {
            result.put("scholarshipNotices", scholarshipNotices);
        }

        // StudentActsNotices 검색 및 DTO 변환
        List<StudentActsNoticeDTO> studentActsNotices = studentActsNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new StudentActsNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!studentActsNotices.isEmpty()) {
            result.put("studentActsNotices", studentActsNotices);
        }

        // BiddingNotices 검색 및 DTO 변환
        List<BiddingNoticeDTO> biddingNotices = biddingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new BiddingNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!biddingNotices.isEmpty()) {
            result.put("biddingNotices", biddingNotices);
        }

        // SafetyNotices 검색 및 DTO 변환
        List<SafetyNoticeDTO> safetyNotices = safetyNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new SafetyNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!safetyNotices.isEmpty()) {
            result.put("safetyNotices", safetyNotices);
        }

        // RevisionNotices 검색 및 DTO 변환
        List<RevisionNoticeDTO> revisionNotices = revisionNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new RevisionNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl()
                )).collect(Collectors.toList());

        if (!revisionNotices.isEmpty()) {
            result.put("revisionNotices", revisionNotices);
        }

        return result;
    }
}