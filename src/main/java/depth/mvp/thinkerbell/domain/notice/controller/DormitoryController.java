package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryEntryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.DormitoryEntryNoticeService;
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

    @Autowired
    private DormitoryEntryNoticeService dormitoryEntryNoticeService;

    @GetMapping("/notices")
    public List<DormitoryNoticeDTO> getAllNotices() {
        return dormitoryNoticeService.getAllNotices();
    }

    @GetMapping("/entry-notices")
    public List<DormitoryEntryNoticeDTO> getAllEntryNotices() {
        return dormitoryEntryNoticeService.getAllEntryNotices();
    }
}
