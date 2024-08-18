package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.notice.dto.*;
import depth.mvp.thinkerbell.domain.notice.entity.*;
import depth.mvp.thinkerbell.domain.notice.repository.*;
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
        List<Bookmark> bookmarks = bookmarkRepository.findByUserAndCategory(user, "DormitoryNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "DormitoryEntryNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "LibraryNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "TeachingNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "JobTrainingNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "NormalNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "AcademicNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "EventNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "CareerNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "ScholarshipNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "StudentActsNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "BiddingNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "SafetyNotice");
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
        bookmarks = bookmarkRepository.findByUserAndCategory(user, "RevisionNotice");
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
}
