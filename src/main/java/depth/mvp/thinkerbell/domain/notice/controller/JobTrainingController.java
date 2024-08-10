package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.JobTrainingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.JobTrainingNoticeService;
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
@RequestMapping("api/job-training")
@RequiredArgsConstructor
public class JobTrainingController {
    @Autowired
    private final JobTrainingNoticeService jobTrainingNoticeService;

    @Operation(summary = "현장실습 참여 기업 status=진행중 조회", description = "현장실습 참여 기업 status가 진행중인 사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<List<JobTrainingNoticeDTO>> getAllJobTrainingNotices() {
        try {
            List<JobTrainingNoticeDTO> notices = jobTrainingNoticeService.getAllJobTrainingNotices();
            return ApiResult.ok(notices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}