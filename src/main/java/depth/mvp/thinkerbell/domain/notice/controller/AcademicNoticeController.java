package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.AcademicNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.AcademicNoticeService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/academic")
@RequiredArgsConstructor
public class AcademicNoticeController {

    @Operation(summary = "명지대 학사 공지사항 조회", description = "명지대 학사 공지사항을 조회합니다. (https://www.mju.ac" +
            ".kr/mjukr/257/subview.do) 중요 공지를 조회할 땐 인자(important)로 true, 그 외 공지를 조회할 땐 false를 전달해주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<List<AcademicNoticeDTO>> getAllAcademicNotices(@RequestParam(value = "important", required = false,
            defaultValue = "false") boolean important,
                                                                    @RequestParam("ssaid") String ssaid) {
        try {
            List<AcademicNoticeDTO> notices = academicNoticeService.getAllAcademicNotices(important, ssaid);
            return ApiResult.ok(notices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    private final AcademicNoticeService academicNoticeService;
}
