package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.common.service.CategoryService;
import depth.mvp.thinkerbell.domain.notice.entity.AllNoticesView;
import depth.mvp.thinkerbell.domain.notice.entity.CrawlingNum;
import depth.mvp.thinkerbell.domain.notice.repository.AllNoticeViewRepository;
import depth.mvp.thinkerbell.domain.notice.repository.CrawlingNumRepository;
import depth.mvp.thinkerbell.domain.user.dto.AlarmDto;
import depth.mvp.thinkerbell.domain.user.entity.Alarm;
import depth.mvp.thinkerbell.domain.user.entity.Bookmark;
import depth.mvp.thinkerbell.domain.user.entity.Keyword;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.AlarmRepository;
import depth.mvp.thinkerbell.domain.user.repository.BookmarkRepository;
import depth.mvp.thinkerbell.domain.user.repository.KeywordRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import depth.mvp.thinkerbell.global.converter.CaseConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AllNoticeViewRepository allNoticeViewRepository;
    private final KeywordRepository keywordRepository;
    private final CrawlingNumRepository crawlingNumRepository;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final FCMService fcmService;
    private final CategoryService categoryService;
    private final BookmarkRepository bookmarkRepository;

    //전체 공지사항이 있는 뷰에서 키워드에 일치하는 공지를 찾아서 알람 테이블에 저장하는 기능
    //이때 최신으로 업데이트된 공지사항만 탐색한다.
    //알람 테이블에 저장되는 것들은 바로 fcm 알림까지 전송된다.
    @Scheduled(cron = "0 0 10-22/3 * * ?")
    public void updateNoticeAndMatchKeyword(){
        List<CrawlingNum> crawlingNums;

        try {
            crawlingNums = crawlingNumRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("크롤링 번호 레코드를 가져오는 동안 오류가 발생했습니다.", e);
        }

        for (CrawlingNum crawlingNum : crawlingNums) {
            List<AllNoticesView> allNoticesViews;

            try {
                allNoticesViews = allNoticeViewRepository.findNewNoticesByCategory(crawlingNum.getNoticeType(), crawlingNum.getNoticeID());

            } catch (Exception e) {
                throw new RuntimeException(crawlingNum.getNoticeType() + "의 새로운 공지사항을 가져오는 중 오류가 발생했습니다.", e);
            }

            for (Keyword keyword : keywordRepository.findAll()) {
                for (AllNoticesView notice : allNoticesViews) {
                    String titleWithoutSpace = notice.getTitle().replace(" ", "");

                    if (titleWithoutSpace.contains(keyword.getKeyword())) {
                        try{
                            Alarm alarm = new Alarm(notice.getId(), notice.getTableName(), keyword.getUser(), notice.getTitle(), keyword.getKeyword());

                            alarmRepository.save(alarm);

                            fcmService.sendFCMMessage(alarm, keyword.getKeyword());
                        } catch (Exception e) {
                            throw new RuntimeException("유저 알림을 저장하거나, fcm 알림을 보내는 도중 오류가 발생했습니다.", e);
                        }
                    }
                }
            }

            //크롤링번호 최신화
            try {
                Long newMaxID = allNoticeViewRepository.findMaxIdByCategory(crawlingNum.getNoticeType());

                Optional<CrawlingNum> existingCrawlingNumOpt = crawlingNumRepository.findByNoticeType(crawlingNum.getNoticeType());

                if (existingCrawlingNumOpt.isPresent()) {
                    // 존재하는 경우 업데이트
                    CrawlingNum existingCrawlingNum = existingCrawlingNumOpt.get();
                    existingCrawlingNum.setNoticeID(newMaxID);
                    crawlingNumRepository.save(existingCrawlingNum);
                } else {
                    // 존재하지 않는 경우 새로 저장
                    CrawlingNum newCrawlingNum = CrawlingNum.builder()
                            .noticeID(newMaxID)
                            .noticeType(crawlingNum.getNoticeType())
                            .build();
                    crawlingNumRepository.save(newCrawlingNum);
                }
            } catch (Exception e) {
                throw new RuntimeException(crawlingNum.getNoticeType() + "의 크롤링 번호를 업데이트 하는 도중 오류가 발생했습니다.", e);
            }
        }
    }

    public void updateNoticeAndMatchKeywordTest(){
        List<CrawlingNum> crawlingNums;

        try {
            crawlingNums = crawlingNumRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("크롤링 번호 레코드를 가져오는 동안 오류가 발생했습니다.", e);
        }

        for (CrawlingNum crawlingNum : crawlingNums) {
            List<AllNoticesView> allNoticesViews;

            try {
                allNoticesViews = allNoticeViewRepository.findNewNoticesByCategory(crawlingNum.getNoticeType(), crawlingNum.getNoticeID());

            } catch (Exception e) {
                throw new RuntimeException(crawlingNum.getNoticeType() + "의 새로운 공지사항을 가져오는 중 오류가 발생했습니다.", e);
            }

            for (Keyword keyword : keywordRepository.findAll()) {
                for (AllNoticesView notice : allNoticesViews) {
                    String titleWithoutSpace = notice.getTitle().replace(" ", "");

                    if (titleWithoutSpace.contains(keyword.getKeyword())) {
                        try{
                            Alarm alarm = new Alarm(notice.getId(), notice.getTableName(), keyword.getUser(), notice.getTitle(), keyword.getKeyword());

                            fcmService.sendFCMMessage(alarm, keyword.getKeyword());
                        } catch (Exception e) {
                            throw new RuntimeException("유저 알림을 저장하거나, fcm 알림을 보내는 도중 오류가 발생했습니다.", e);
                        }
                    }
                }
            }
        }
    }

    //보지 않은 알림이 있으면 true, 없으면 false, 키워드 별로
    public boolean hasUnviewedAlarm(String SSAID, String keyword){
        Optional<User> userOpt = userRepository.findBySsaid(SSAID);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            return alarmRepository.existsByUserIdAndKeywordAndIsViewedFalse(user.getId(), keyword);
        }
        return false;
    }

    public boolean hasUnviewedAllAlarm(String SSAID){
        Optional<User> userOpt = userRepository.findBySsaid(SSAID);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            return alarmRepository.existsByUserIdAndIsViewedFalse(user.getId());
        }
        return false;
    }

    //안본거 본걸로 바꾸기
    public void markAsViewed(Long alarmID){
        Alarm alarm = alarmRepository.findById(alarmID)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 알림이 없습니다."));
        alarm.setIsViewed(true);
    }

    //알림 키워드, 사용자 기반 조회
    public List<AlarmDto> getAlarms(String SSAID, String keyword){
        Optional<User> userOpt = userRepository.findBySsaid(SSAID);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            List<Alarm> alarms = alarmRepository.findALLByUserIdAndKeyword(user.getId(), keyword);

            List<AlarmDto> alarmDtos = new ArrayList<>();

            for (Alarm alarm : alarms) {
                String noticeType = categoryService.getCategoryUpper(alarm.getNoticeType());

                List<Bookmark> bookmark = bookmarkRepository.findByCategoryAndUserAndNoticeID(noticeType, user, alarm.getNoticeID());

                boolean isMarked = true;

                if (bookmark.isEmpty()) {
                    isMarked = false;
                }

                if (Objects.equals(alarm.getNoticeType(), "job_training_notice")){
                    String pubDate = getNoticeDetail(alarm.getNoticeType(), alarm.getNoticeID());

                    AlarmDto alarmDto = AlarmDto.builder()
                            .id(alarm.getId())
                            .title(alarm.getTitle())
                            .noticeType_korean(categoryService.getCategoryNameInKorean(alarm.getNoticeType()))
                            .noticeType_english(CaseConverter.snakeToPascal(alarm.getNoticeType()))
                            .isViewed(alarm.getIsViewed())
                            .isMarked(isMarked)
                            .Url(null)
                            .pubDate(pubDate)
                            .build();

                    alarmDtos.add(alarmDto);
                } else {
                    Map<String, Object> noticeDetails = getNoticeDetails(alarm.getNoticeType(), alarm.getNoticeID());

                    String url = (String) noticeDetails.get("url");
                    String pubDate = (String) noticeDetails.get("pubDate");

                    AlarmDto alarmDto = AlarmDto.builder()
                            .id(alarm.getId())
                            .title(alarm.getTitle())
                            .noticeType_korean(categoryService.getCategoryNameInKorean(alarm.getNoticeType()))
                            .noticeType_english(CaseConverter.snakeToPascal(alarm.getNoticeType()))
                            .isViewed(alarm.getIsViewed())
                            .isMarked(isMarked)
                            .Url(url)
                            .pubDate(pubDate)
                            .build();

                    alarmDtos.add(alarmDto);
                }
            }

            return alarmDtos;
        } else {
            return Collections.emptyList();
        }
    }

    private Map<String, Object> getNoticeDetails(String tableName, Long noticeID) {
        String sql = "SELECT url, DATE_FORMAT(pub_date, '%Y-%m-%d') as pubDate FROM " + tableName + " WHERE id = :noticeID";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("noticeID", noticeID);

        List<Object[]> results = query.getResultList();

        if (!results.isEmpty()) {
            Object[] row = results.get(0);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("url", row[0]);
            resultMap.put("pubDate", row[1]);
            return resultMap;
        } else {
            return Collections.emptyMap();
        }
    }

    public String getNoticeDetail(String tableName, Long noticeID) {
        String sql = "SELECT semester FROM " + tableName + " WHERE id = :noticeID";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("noticeID", noticeID);

        Object result = query.getSingleResult();
        return result != null ? result.toString() : null;
    }
}
