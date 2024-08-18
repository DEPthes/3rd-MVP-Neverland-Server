package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.repository.*;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
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
    private final BookmarkService bookmarkService;
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


    public Map<String, List<?>> searchNotices(String keyword, String ssaid) {
        Map<String, List<?>> result = new HashMap<>();
        // USER가 북마크한 내역(id리스트) 가져오기
        List<Long> bookmarkedNoticeIds = bookmarkService.getBookmark(ssaid,
                this.getClass().getSimpleName().replace("Service", ""));
        // DormitoryEntryNotice 검색 및 DTO 변환
        List<DormitoryEntryNoticeDTO> dormitoryEntryNotices = dormitoryEntryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
            result.put("dormitoryEntryNotices", dormitoryEntryNotices);
        }

        // DormitoryNotice 검색 및 DTO 변환
        List<DormitoryNoticeDTO> dormitoryNotices = dormitoryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
            result.put("dormitoryNotices", dormitoryNotices);
        }

        // LibraryNotice 검색 및 DTO 변환
        List<LibraryNoticeDTO> libraryNotices = libraryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
            result.put("libraryNotices", libraryNotices);
        }

       //  TeachingNotice 검색 및 DTO 변환
        List<TeachingNoticeDTO> teachingNotices = teachingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
            result.put("teachingNotices", teachingNotices);
        }

        // JobTraingingNotice 검색 및 DTO 변환
        List<JobTrainingNoticeDTO> jobTrainingNotices = jobTrainingNoticeRepository.searchByTitleOrMajor(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());
                    return JobTrainingNoticeDTO.builder()
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
            result.put("jobTrainingNotices", jobTrainingNotices);
        }

        // NormalNotice 검색 및 DTO 변환
        List<NormalNoticeDTO> normalNotices = normalNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
            result.put("normalNotices", normalNotices);
        }

        // AcademicNotice 검색 및 DTO 변환
        List<AcademicNoticeDTO> academicNotices = academicNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
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

        if (!academicNotices.isEmpty()) {
            result.put("academicNotices", academicNotices);
        }

        // EventNotices 검색 및 DTO 변환
        List<EventNoticeDTO> eventNotices = eventNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return EventNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!eventNotices.isEmpty()) {
            result.put("eventNotices", eventNotices);
        }

        // CareerNotice 검색 및 DTO 변환
        List<CareerNoticeDTO> careerNotices = careerNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

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
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return ScholarshipNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!scholarshipNotices.isEmpty()) {
            result.put("scholarshipNotices", scholarshipNotices);
        }

        // StudentActsNotices 검색 및 DTO 변환
        List<StudentActsNoticeDTO> studentActsNotices = studentActsNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return StudentActsNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        // BiddingNotices 검색 및 DTO 변환
        if (!studentActsNotices.isEmpty()) {
            result.put("StudentActsNotice", studentActsNotices);
        }

        List<BiddingNoticeDTO> biddingNotices = biddingNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return BiddingNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!biddingNotices.isEmpty()) {
            result.put("biddingNotices", biddingNotices);
        }

        // SafetyNotices 검색 및 DTO 변환
        List<SafetyNoticeDTO> safetyNotices = safetyNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return SafetyNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!safetyNotices.isEmpty()) {
            result.put("safetyNotices", safetyNotices);
        }

        // RevisionNotices 검색 및 DTO 변환
        List<RevisionNoticeDTO> revisionNotices = revisionNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> {
                    boolean isMarked = bookmarkedNoticeIds.contains(notice.getId());

                    return RevisionNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build();
                }).collect(Collectors.toList());

        if (!revisionNotices.isEmpty()) {
            result.put("revisionNotices", revisionNotices);
        }

        return result;
    }
}