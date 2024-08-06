package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryEntryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.DormitoryEntryNoticeService;
import depth.mvp.thinkerbell.domain.notice.service.DormitoryNoticeService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "일반 공지사항 조회", description = "생활관 일반 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/notices")
    public ApiResult<List<DormitoryNoticeDTO>> getAllNotices() {
        try {
            List<DormitoryNoticeDTO> notices = dormitoryNoticeService.getAllNotices();
            return ApiResult.ok(notices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "입퇴사 공지사항 조회", description = "생활관 입퇴사 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/entry-notices")
    public ApiResult<List<DormitoryEntryNoticeDTO>> getAllEntryNotices() {
        try {
            List<DormitoryEntryNoticeDTO> entryNotices = dormitoryEntryNoticeService.getAllEntryNotices();
            return ApiResult.ok(entryNotices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
