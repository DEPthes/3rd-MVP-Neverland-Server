package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.DormitoryNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/dormitory")
@RequiredArgsConstructor
public class DormitoryController {
    @Autowired
    private DormitoryNoticeService dormitoryNoticeService;

    @GetMapping
    public List<DormitoryNoticeDTO> getAllNotices() {
        return dormitoryNoticeService.getAllNotices();
    }
}
