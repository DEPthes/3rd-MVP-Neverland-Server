package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.RevisionNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.RevisionNoticeService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/revision")
@RequiredArgsConstructor
public class RevisionNoticeController {

    private final RevisionNoticeService revisionNoticeService;

    @Operation(summary = "명지대 학칙개정 사전공고 조회", description = "명지대 학칙개정 사전공고를 조회합니다. (https://www.mju.ac" +
            ".kr/mjukr/4450/subview.do)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<List<RevisionNoticeDTO>> getAllRevisionNotices() {
        try {
            List<RevisionNoticeDTO> notices = revisionNoticeService.getAllRevisionNotices();
            return ApiResult.ok(notices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
