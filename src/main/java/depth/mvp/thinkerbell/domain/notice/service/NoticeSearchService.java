package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.repository.*;
import depth.mvp.thinkerbell.domain.user.entity.Bookmark;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeSearchService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final DormitoryEntryNoticeRepository dormitoryEntryNoticeRepository;
    private final DormitoryNoticeRepository dormitoryNoticeRepository;
    private final LibraryNoticeRepository libraryNoticeRepository;
    private final TeachingNoticeRepository teachingNoticeRepository;
    private final JobTrainingNoticeRepository jobTrainingNoticeRepository;
    private final NormalNoticeRepository normalNoticeRepository;
    private final AcademicNoticeRepository academicNoticeRepository;
    private final EventNoticeRepository eventNoticeRepository;
    private final CareerNoticeRepository careerNoticeRepository;
    private final ScholarshipNoticeRepository scholarshipNoticeRepository;
    private final StudentActsNoticeRepository studentActsNoticeRepository;
    private final BiddingNoticeRepository biddingNoticeRepository;
    private final SafetyNoticeRepository safetyNoticeRepository;
    private final RevisionNoticeRepository revisionNoticeRepository;


    public Map<String, List<?>> searchNotices(String keyword, String ssaid) {
        Map<String, List<?>> result = new HashMap<>();
        // USER가 북마크한 내역(id리스트) 가져오기
        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // DormitoryEntryNotice 검색 및 DTO 변환
        List<DormitoryEntryNoticeDTO> dormitoryEntryNotices = dormitoryEntryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "DormitoryEntryNotice", notice.getId(), user);
                    return DormitoryEntryNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .campus(notice.getCampus())
                            .build();
                })
                .collect(Collectors.toList());
        if (!dormitoryEntryNotices.isEmpty()) {
            result.put("DormitoryEntryNotice", dormitoryEntryNotices);
        }

        // DormitoryNotice 검색 및 DTO 변환
        List<DormitoryNoticeDTO> dormitoryNotices = dormitoryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "DormitoryNotice", notice.getId(), user);
                    return DormitoryNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .campus(notice.getCampus())
                            .build();
                })
                .collect(Collectors.toList());

        if (!dormitoryNotices.isEmpty()) {
            result.put("DormitoryNotice", dormitoryNotices);
        }

        // LibraryNotice 검색 및 DTO 변환
        List<LibraryNoticeDTO> libraryNotices = libraryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "LibraryNotice", notice.getId(), user);
                    return LibraryNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .campus(notice.getCampus())
                            .build();
                })
                .collect(Collectors.toList());

        if (!libraryNotices.isEmpty()) {
            result.put("LibraryNotice", libraryNotices);
        }

       //  TeachingNotice 검색 및 DTO 변환
        List<TeachingNoticeDTO> teachingNotices = teachingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "TeachingNotice", notice.getId(), user);
                    return TeachingNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .build();
                })
                .collect(Collectors.toList());

        if (!teachingNotices.isEmpty()) {
            result.put("TeachingNotice", teachingNotices);
        }

        // JobTrainingNotice 검색 및 DTO 변환
        List<JobTrainingNoticeDTO> jobTrainingNotices = jobTrainingNoticeRepository.searchByTitleOrMajor(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "JobTrainingNotice", notice.getId(), user);                    return JobTrainingNoticeDTO.builder()
                            .id(notice.getId())
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
                .collect(Collectors.toList());

        if (!jobTrainingNotices.isEmpty()) {
            result.put("JobTrainingNotice", jobTrainingNotices);
        }

        // NormalNotice 검색 및 DTO 변환
        List<NormalNoticeDTO> normalNotices = normalNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "NormalNotice", notice.getId(), user);
                    return NormalNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .build();
                }).collect(Collectors.toList());
        if (!normalNotices.isEmpty()) {
            result.put("NormalNotice", normalNotices);
        }

        // AcademicNotice 검색 및 DTO 변환
        List<AcademicNoticeDTO> academicNotices = academicNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "AcademicNotice", notice.getId(), user);
                    return AcademicNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .build();
                }).collect(Collectors.toList());

        if (!academicNotices.isEmpty()) {
            result.put("AcademicNotice", academicNotices);
        }

        // EventNotice 검색 및 DTO 변환
        List<EventNoticeDTO> eventNotices = eventNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "EventNotice", notice.getId(), user);
                    return EventNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!eventNotices.isEmpty()) {
            result.put("EventNotice", eventNotices);
        }

        // CareerNotice 검색 및 DTO 변환
        List<CareerNoticeDTO> careerNotices = careerNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "CareerNotice", notice.getId(), user);
                    return CareerNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!careerNotices.isEmpty()) {
            result.put("CareerNotice", careerNotices);
        }

        // ScholarshipNotice 검색 및 DTO 변환
        List<ScholarshipNoticeDTO> scholarshipNotices = scholarshipNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "ScholarshipNotices", notice.getId(), user);
                    return ScholarshipNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!scholarshipNotices.isEmpty()) {
            result.put("ScholarshipNotices", scholarshipNotices);
        }

        // StudentActsNotice 검색 및 DTO 변환
        List<StudentActsNoticeDTO> studentActsNotices = studentActsNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "StudentActsNotice", notice.getId(), user);
                    return StudentActsNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!studentActsNotices.isEmpty()) {
            result.put("StudentActsNotice", studentActsNotices);
        }

        // BiddingNotice 검색 및 DTO 변환
        List<BiddingNoticeDTO> biddingNotices = biddingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "BiddingNotice", notice.getId(), user);
                    return BiddingNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!biddingNotices.isEmpty()) {
            result.put("BiddingNotice", biddingNotices);
        }

        // SafetyNotice 검색 및 DTO 변환
        List<SafetyNoticeDTO> safetyNotices = safetyNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "SafetyNotice", notice.getId(), user);
                    return SafetyNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!safetyNotices.isEmpty()) {
            result.put("SafetyNotice", safetyNotices);
        }

        // RevisionNotice 검색 및 DTO 변환
        List<RevisionNoticeDTO> revisionNotices = revisionNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkRepository.existsByCategoryAndNoticeIDAndUser(
                            "RevisionNotice", notice.getId(), user);
                    return RevisionNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!revisionNotices.isEmpty()) {
            result.put("RevisionNotice", revisionNotices);
        }

        return result;
    }
}