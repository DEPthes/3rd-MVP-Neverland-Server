package depth.mvp.thinkerbell.domain.user.controller;

import depth.mvp.thinkerbell.domain.user.dto.KeywordDto;
import depth.mvp.thinkerbell.domain.user.service.KeywordDeleteService;
import depth.mvp.thinkerbell.domain.user.service.KeywordSaveService;
import depth.mvp.thinkerbell.domain.user.service.KeywordService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/keyword")
@RequiredArgsConstructor
public class KeywordController {

    public final KeywordSaveService keywordSaveService;
    public final KeywordDeleteService keywordDeleteService;
    public final KeywordService keywordService;

    @Operation(summary = "키워드 저장", description = "주어진 키워드를 저장합니다. 키워드가 2글자 이상이어야 하며, 이미 사용된 이력이 있어야합니다. 공백은 무시됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 저장됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/save")
    public ApiResult<String> saveKeyword(@RequestParam String keyword, @RequestParam String userSSAID) {

        if (!keywordSaveService.isExistKeyword(keyword)){
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "해당 키워드는 존재하지 않습니다.");
        }

        if (!keywordSaveService.countKeyword(keyword)){
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "키워드는 두글자 이상이여야 합니다.");
        }

        if (keywordSaveService.isDuplicateKeyword(keyword, userSSAID)){
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "이미 등록된 키워드 입니다.");
        }

        if (!keywordSaveService.overNineKeyword(userSSAID)){
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "키워드는 9개까지 저장 가능합니다.");
        }

        if (keywordSaveService.saveKeywordToDB(keyword, userSSAID)){
            return ApiResult.ok("키워드가 성공적으로 저장되었습니다.");
        } else {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, "키워드 저장에 실패했습니다. 사용자를 찾을 수 없습니다.");
        }
    }

    @Operation(summary = "키워드 삭제", description = "주어진 키워드를 삭제합니다. 키워드가 삭제 되었는지 아닌지 확인가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 저장됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/delete")
    public ApiResult<String> deleteKeyword(@RequestParam String keyword, @RequestParam String userSSAID) {

        if (keywordDeleteService.isDeleted(keyword, userSSAID)){
            return ApiResult.ok("키워드가 성공적으로 삭제되었습니다.");
        } else {
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "키워드를 삭제하지 못했습니다. 사용자를 찾을수 없거나 키워드가 존재하지 않습니다.");
        }
    }

    @Operation(summary = "키워드 조회", description = "주어진 사용자가 저장한 키워드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 저장됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<List<KeywordDto>> getKeywords(
            @Parameter(description = "사용자 SSAID", required = true)
            @RequestParam String userSSAID) {
        try {
            List<KeywordDto> keywords = keywordService.getKeywords(userSSAID);
            return ApiResult.ok(keywords);
        } catch (IllegalArgumentException e) {
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, null);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
