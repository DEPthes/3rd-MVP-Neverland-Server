package depth.mvp.thinkerbell.domain.user.controller;

import depth.mvp.thinkerbell.domain.user.dto.UserDto;
import depth.mvp.thinkerbell.domain.user.service.UserService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user-info")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "디바이스 정보 저장", description = "주어진 SSAID, FCM Token을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 저장됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @PostMapping("/save")
    public ApiResult<String> saveUserInfo(@RequestBody UserDto userDto) {
        try {
            userService.saveUser(userDto);

            return ApiResult.ok("성공적으로 저장됨");
        } catch (IllegalArgumentException e) {
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, "잘못된 입력 값");
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, "서버 오류 발생");
        }
    }
}
