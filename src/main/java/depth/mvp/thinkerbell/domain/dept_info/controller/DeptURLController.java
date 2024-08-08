package depth.mvp.thinkerbell.domain.dept_info.controller;

import depth.mvp.thinkerbell.domain.dept_info.dto.DeptURLDto;
import depth.mvp.thinkerbell.domain.dept_info.service.DeptURLService;
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
@RequestMapping("api/dept-url")
@RequiredArgsConstructor
public class DeptURLController {

    private final DeptURLService deptURLService;

    @Operation(summary = "단과 대학 및 본부 기관 사이트 url 조회", description = "단과 대학 및 본부 기관 사이트 url을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ApiResult<List<DeptURLDto>> getDeptURL() {
        try{
            List<DeptURLDto> url = deptURLService.getAllDeptURL();
            return ApiResult.ok(url);
        } catch (IllegalArgumentException e){
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, null);
        } catch (RuntimeException e){
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
