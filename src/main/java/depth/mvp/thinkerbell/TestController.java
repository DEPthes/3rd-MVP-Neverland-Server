package depth.mvp.thinkerbell;

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

    @GetMapping("/test")
    public String test(){
        return "배포 테스트";
    }

}
