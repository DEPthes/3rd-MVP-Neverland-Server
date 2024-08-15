package depth.mvp.thinkerbell.domain.user.service;

import com.google.firebase.messaging.*;
import depth.mvp.thinkerbell.domain.common.service.CategoryService;
import depth.mvp.thinkerbell.domain.user.entity.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMService {

    private final CategoryService categoryService;

    public void sendFCMMessage(Alarm alarm, String keyword) {
        try{
            String category = categoryService.getCategoryNameInKorean(alarm.getNoticeType());
            String cutTitle = cutTitle(alarm.getTitle(), 30);

            String messageBody = String.format("‘띵~🔔 %s와(과) 관련한 공지가 올라왔어요!’\n\n[%s] %s",
                    keyword, category, cutTitle);

            Message message = Message.builder()
                    .setAndroidConfig(AndroidConfig.builder()
                            .setNotification(AndroidNotification.builder()
                                    .setTitle("띵커벨")
                                    .setBody(messageBody)
                                    .build())
                            .build())
                    .setToken(alarm.getUser().getFcmToken())
                    .build();

            String responce = FirebaseMessaging.getInstance().send(message);
            System.out.println("전송 성공" + responce);
        } catch (Exception e){
            throw new RuntimeException("FCM 알림을 전송하는 동안 오류가 발생했습니다.",e);
        }
    }

    private String cutTitle(String title, int maxLength) {
        if (title.length() > maxLength) {
            return title.substring(0, maxLength - 1) + "…"; // 말줄임표 추가
        }
        return title;
    }
}