package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.entity.*;
import depth.mvp.thinkerbell.domain.notice.repository.*;
import depth.mvp.thinkerbell.domain.user.dto.RecentMarkedNoticeDto;
import depth.mvp.thinkerbell.domain.user.entity.Bookmark;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

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

    public List<Long> getBookmark(String ssaid, String category) {
        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return bookmarkRepository.findByUserAndCategory(user, category)
                .stream()
                .map(Bookmark::getNoticeID).toList();
    }
    public Bookmark saveBookmark(String category, Long noticeId, String ssaid) {

        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        if (bookmarkRepository.findByCategoryAndNoticeIDAndUser(category, noticeId, user) != null){
            throw new NotFoundException("이미 즐겨찾기한 공지입니다.");
        }else {
            return bookmarkRepository.save(Bookmark.builder()
                    .category(category)
                    .noticeID(noticeId)
                    .user(user)
                    .build());
        }
    }
    public void deleteBookmark(String category, Long noticeId, String ssaid) {

        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        Bookmark bookmark = bookmarkRepository.findByCategoryAndNoticeIDAndUser(category, noticeId, user);
        if (bookmark == null) {
            new NotFoundException("즐겨찾기 내역을 찾을 수 없습니다.");
        }
        bookmarkRepository.deleteById(bookmark.getId());
    }

    public Map<String, List<?>> getMarkedNotices(String ssaid) {
        Map<String, List<?>> result = new HashMap<>();
        // USER가 북마크한 내역(id리스트) 가져오기
        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        boolean isMarked = true;

        // DormitoryNotice
        List<Bookmark> bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "DormitoryNotice");
        List<DormitoryNoticeDTO> markedDormitoryNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
                DormitoryNotice notice = dormitoryNoticeRepository.findOneById(bookmark.getNoticeID());
                if (notice!= null){
                    markedDormitoryNoticeDtos.add(DormitoryNoticeDTO.builder()
                                    .id(notice.getId())
                                    .pubDate(notice.getPubDate())
                                    .title(notice.getTitle())
                                    .url(notice.getUrl())
                                    .marked(isMarked)
                                    .important(notice.isImportant())
                                    .campus(notice.getCampus())
                                    .build());
                }
        }
        if (!markedDormitoryNoticeDtos.isEmpty()) {
            result.put("DormitoryNotice", markedDormitoryNoticeDtos);
        }

        // DormitoryEntryNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "DormitoryEntryNotice");
        List<DormitoryEntryNoticeDTO> markedDormitoryEntryNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            DormitoryEntryNotice notice = dormitoryEntryNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedDormitoryEntryNoticeDtos.add(DormitoryEntryNoticeDTO.builder()
                                    .id(notice.getId())
                                    .pubDate(notice.getPubDate())
                                    .title(notice.getTitle())
                                    .url(notice.getUrl())
                                    .marked(isMarked)
                                    .important(notice.isImportant())
                                    .campus(notice.getCampus())
                                    .build());
            }
        }
        if (!markedDormitoryEntryNoticeDtos.isEmpty()) {
            result.put("DormitoryEntryNotice", markedDormitoryEntryNoticeDtos);
        }

        // LibraryNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "LibraryNotice");
        List<LibraryNoticeDTO> markedLibraryNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            LibraryNotice notice = libraryNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedLibraryNoticeDtos.add(LibraryNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                            .campus(notice.getCampus())
                            .build());
            }
        }
        if (!markedLibraryNoticeDtos.isEmpty()) {
            result.put("LibraryNotice", markedLibraryNoticeDtos);
        }

        // TeachingNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "TeachingNotice");
        List<TeachingNoticeDTO> markedTeachingNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            TeachingNotice notice = teachingNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedTeachingNoticeDtos.add(TeachingNoticeDTO.builder()
                                .id(notice.getId())
                                .pubDate(notice.getPubDate())
                                .title(notice.getTitle())
                                .url(notice.getUrl())
                                .marked(isMarked)
                                .important(notice.isImportant())
                                .build());
            }
        }
        if (!markedTeachingNoticeDtos.isEmpty()) {
            result.put("TeachingNotice", markedTeachingNoticeDtos);
        }

        // JobTrainingNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "JobTrainingNotice");
        List<JobTrainingNoticeDTO> markedJobTrainingNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            JobTrainingNotice notice = jobTrainingNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedJobTrainingNoticeDtos.add(JobTrainingNoticeDTO.builder()
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
                        .build());
            }
        }
        if (!markedJobTrainingNoticeDtos.isEmpty()) {
            result.put("JobTrainingNotice", markedJobTrainingNoticeDtos);
        }

        // NormalNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "NormalNotice");
        List<NormalNoticeDTO> markedNormalNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            NormalNotice notice = normalNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedNormalNoticeDtos.add(NormalNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .important(notice.isImportant())
                        .build());
            }
        }
        if (!markedNormalNoticeDtos.isEmpty()) {
            result.put("markedNormalNotice", markedNormalNoticeDtos);
        }

        // AcademicNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "AcademicNotice");
        List<AcademicNoticeDTO> markedAcademicNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            AcademicNotice notice = academicNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedAcademicNoticeDtos.add(AcademicNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .important(notice.isImportant())
                        .build());
            }
        }
        if (!markedAcademicNoticeDtos.isEmpty()) {
            result.put("AcademicNotice", markedAcademicNoticeDtos);
        }

        // EventNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "EventNotice");
        List<EventNoticeDTO> markedEventNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            EventNotice notice = eventNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedEventNoticeDtos.add(EventNoticeDTO.builder()
                            .id(notice.getId())
                            .pubDate(notice.getPubDate())
                            .title(notice.getTitle())
                            .url(notice.getUrl())
                            .marked(isMarked)
                            .build());
            }
        }
        if (!markedEventNoticeDtos.isEmpty()) {
            result.put("EventNotice", markedEventNoticeDtos);
        }

        // CareerNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "CareerNotice");
        List<CareerNoticeDTO> markedCareerNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            CareerNotice notice = careerNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedCareerNoticeDtos.add(CareerNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedCareerNoticeDtos.isEmpty()) {
            result.put("CareerNotice", markedCareerNoticeDtos);
        }

        // ScholarshipNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "ScholarshipNotice");
        List<ScholarshipNoticeDTO> markedScholarshipNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            ScholarshipNotice notice = scholarshipNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedScholarshipNoticeDtos.add(ScholarshipNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedScholarshipNoticeDtos.isEmpty()) {
            result.put("ScholarshipNotice", markedScholarshipNoticeDtos);
        }

        // StudentActsNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "StudentActsNotice");
        List<StudentActsNoticeDTO> markedStudentActsNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            StudentActsNotice notice = studentActsNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedStudentActsNoticeDtos.add(StudentActsNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedStudentActsNoticeDtos.isEmpty()) {
            result.put("StudentActsNotice", markedStudentActsNoticeDtos);
        }

        // BiddingNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "BiddingNotice");
        List<BiddingNoticeDTO> markedBiddingNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            BiddingNotice notice = biddingNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedBiddingNoticeDtos.add(BiddingNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedBiddingNoticeDtos.isEmpty()) {
            result.put("BiddingNotice", markedBiddingNoticeDtos);
        }

        // SafetyNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "SafetyNotice");
        List<SafetyNoticeDTO> markedSafetyNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            SafetyNotice notice = safetyNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedSafetyNoticeDtos.add(SafetyNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedSafetyNoticeDtos.isEmpty()) {
            result.put("SafetyNotice", markedSafetyNoticeDtos);
        }

        // RevisionNotice
        bookmarks = bookmarkRepository.findByUserAndCategoryOrderByCreatedAtDesc(user, "RevisionNotice");
        List<RevisionNoticeDTO> markedRevisionNoticeDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks){
            RevisionNotice notice = revisionNoticeRepository.findOneById(bookmark.getNoticeID());
            if (notice!= null){
                markedRevisionNoticeDtos.add(RevisionNoticeDTO.builder()
                        .id(notice.getId())
                        .pubDate(notice.getPubDate())
                        .title(notice.getTitle())
                        .url(notice.getUrl())
                        .marked(isMarked)
                        .build());
            }
        }
        if (!markedRevisionNoticeDtos.isEmpty()) {
            result.put("RevisionNotice", markedRevisionNoticeDtos);
        }
        return result;
    }

    public List<RecentMarkedNoticeDto> getRecentNotices(String ssaid) {
        List<RecentMarkedNoticeDto> recentMarkedNoticeDtos = new ArrayList<>();
        User user = userRepository.findBySsaid(ssaid)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // 카테고리 목록
        List<String> categories = List.of(
                "DormitoryNotice", "DormitoryEntryNotice", "LibraryNotice",
                "TeachingNotice", "JobTrainingNotice", "NormalNotice",
                "AcademicNotice", "EventNotice", "CareerNotice",
                "ScholarshipNotice", "StudentActsNotice", "BiddingNotice",
                "SafetyNotice", "RevisionNotice"
        );

        // 각 카테고리별로 최근 북마크된 공지사항 가져오기
        for (String category : categories) {
            List<Bookmark> bookmarks = bookmarkRepository.findTop3ByUserAndCategoryOrderByCreatedAtDesc(user, category);

            for (Bookmark bookmark : bookmarks) {
                Long noticeId = bookmark.getNoticeID();
                switch (category) {
                    case "DormitoryNotice":
                        DormitoryNotice dormitoryNotice = dormitoryNoticeRepository.findOneById(noticeId);
                        if (dormitoryNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(dormitoryNotice.getTitle())
                                    .pubDate(dormitoryNotice.getPubDate())
                                    .url(dormitoryNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "DormitoryEntryNotice":
                        DormitoryEntryNotice dormitoryEntryNotice = dormitoryEntryNoticeRepository.findOneById(noticeId);
                        if (dormitoryEntryNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(dormitoryEntryNotice.getTitle())
                                    .pubDate(dormitoryEntryNotice.getPubDate())
                                    .url(dormitoryEntryNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "LibraryNotice":
                        LibraryNotice libraryNotice = libraryNoticeRepository.findOneById(noticeId);
                        if (libraryNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(libraryNotice.getTitle())
                                    .pubDate(libraryNotice.getPubDate())
                                    .url(libraryNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "TeachingNotice":
                        TeachingNotice teachingNotice = teachingNoticeRepository.findOneById(noticeId);
                        if (teachingNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(teachingNotice.getTitle())
                                    .pubDate(teachingNotice.getPubDate())
                                    .url(teachingNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "NormalNotice":
                        NormalNotice normalNotice = normalNoticeRepository.findOneById(noticeId);
                        if (normalNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(normalNotice.getTitle())
                                    .pubDate(normalNotice.getPubDate())
                                    .url(normalNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "AcademicNotice":
                        AcademicNotice academicNotice = academicNoticeRepository.findOneById(noticeId);
                        if (academicNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(academicNotice.getTitle())
                                    .pubDate(academicNotice.getPubDate())
                                    .url(academicNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "EventNotice":
                        EventNotice eventNotice = eventNoticeRepository.findOneById(noticeId);
                        if (eventNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(eventNotice.getTitle())
                                    .pubDate(eventNotice.getPubDate())
                                    .url(eventNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "CareerNotice":
                        CareerNotice careerNotice = careerNoticeRepository.findOneById(noticeId);
                        if (careerNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(careerNotice.getTitle())
                                    .pubDate(careerNotice.getPubDate())
                                    .url(careerNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "ScholarshipNotice":
                        ScholarshipNotice scholarshipNotice = scholarshipNoticeRepository.findOneById(noticeId);
                        if (scholarshipNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(scholarshipNotice.getTitle())
                                    .pubDate(scholarshipNotice.getPubDate())
                                    .url(scholarshipNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "StudentActsNotice":
                        StudentActsNotice studentActsNotice = studentActsNoticeRepository.findOneById(noticeId);
                        if (studentActsNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(studentActsNotice.getTitle())
                                    .pubDate(studentActsNotice.getPubDate())
                                    .url(studentActsNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "BiddingNotice":
                        BiddingNotice biddingNotice = biddingNoticeRepository.findOneById(noticeId);
                        if (biddingNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(biddingNotice.getTitle())
                                    .pubDate(biddingNotice.getPubDate())
                                    .url(biddingNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "SafetyNotice":
                        SafetyNotice safetyNotice = safetyNoticeRepository.findOneById(noticeId);
                        if (safetyNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(safetyNotice.getTitle())
                                    .pubDate(safetyNotice.getPubDate())
                                    .url(safetyNotice.getUrl())
                                    .build());
                        }
                        break;
                    case "RevisionNotice":
                        RevisionNotice revisionNotice = revisionNoticeRepository.findOneById(noticeId);
                        if (revisionNotice != null) {
                            recentMarkedNoticeDtos.add(RecentMarkedNoticeDto.builder()
                                    .category(category)
                                    .title(revisionNotice.getTitle())
                                    .pubDate(revisionNotice.getPubDate())
                                    .url(revisionNotice.getUrl())
                                    .build());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return recentMarkedNoticeDtos;
    }

}
