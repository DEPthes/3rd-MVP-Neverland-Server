package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.AcademicNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.NormalNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.service.NormalNoticeService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/normal")
@RequiredArgsConstructor
public class NormalNoticeController {

    private final NormalNoticeService normalNoticeService;

    @Operation(summary = "명지대 일반 공지사항 조회", description = "명지대 일반 공지사항을 조회합니다. (https://www.mju.ac" +
            ".kr/mjukr/255/subview.do)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<PaginationDTO<NormalNoticeDTO>> getAllNormalNotices(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("ssaid") String ssaid) {
        try {
            PaginationDTO<NormalNoticeDTO> paginationDTO = normalNoticeService.getAllNormalNotices(page, size, ssaid);
            return ApiResult.ok(paginationDTO);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
