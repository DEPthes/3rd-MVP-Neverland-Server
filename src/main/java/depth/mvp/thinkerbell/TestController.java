package depth.mvp.thinkerbell;

import depth.mvp.thinkerbell.domain.user.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TestController {

    private final AlarmService alarmService;

    @GetMapping("/test")
    public String test(){
        return "배포 CI/CD 테스트";
    }

    @GetMapping("/alarm-test")
    public void testAlarm(String ssaid, String keyword){
        alarmService.updateNoticeAndMatchKeywordTest(ssaid, keyword);
    }

}
