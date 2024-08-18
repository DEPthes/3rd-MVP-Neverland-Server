package depth.mvp.thinkerbell.domain.user.controller;

import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "즐겨찾기 설정", description = "공지사항/학사일정을 즐겨찾기합니다. (false -> true)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 저장됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("")
    public ApiResult<?> saveBookmark(@RequestParam("category") String category,
                                     @RequestParam("notice-id") Long noticeId,
                                     @RequestParam("ssaid") String ssaid) {
        try {
            bookmarkService.saveBookmark(category, noticeId, ssaid);
            return ApiResult.ok("성공적으로 저장됨");
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "즐겨찾기 취소", description = "공지사항/학사일정을을 즐겨찾기 취소합니다. (true -> false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 삭제됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @DeleteMapping("")
    public ApiResult<?> deleteBookmark(@RequestParam("category") String category,
                                     @RequestParam("notice-id") Long noticeId,
                                     @RequestParam("ssaid") String ssaid) {
        try {
            bookmarkService.deleteBookmark(category, noticeId, ssaid);
            return ApiResult.ok("성공적으로 삭제됨");
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "공지사항 즐겨찾기 내역 조회", description = "즐겨찾기 설정한 공지사항을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("")
    public ApiResult<Map<String, List<?>>> getMarkedNotices(@RequestParam("ssaid") String ssaid) {
        try {
            return ApiResult.ok(bookmarkService.getMarkedNotices(ssaid));
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
