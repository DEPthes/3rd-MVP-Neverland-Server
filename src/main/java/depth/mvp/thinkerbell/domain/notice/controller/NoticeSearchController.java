package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.service.NoticeSearchService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeSearchController {

    private final NoticeSearchService noticeSearchService;

    @Operation(summary = "공지 카테고리 별 검색 및 반환", description = "공지 카테고리 별 검색 및 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/search")
    public ApiResult<Map<String, List<?>>> searchNotices(@RequestParam String keyword,
                                                         @RequestParam String ssaid) {
        try {
            Map<String, List<?>> notices = noticeSearchService.searchNotices(keyword, ssaid);
            return ApiResult.ok(notices);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }

    }
}
