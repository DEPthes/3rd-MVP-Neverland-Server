package depth.mvp.thinkerbell.domain.notice.controller;

import depth.mvp.thinkerbell.domain.notice.dto.AcademicScheduleDto;
import depth.mvp.thinkerbell.domain.notice.service.AcademicScheduleService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/academic-schedule")
@RequiredArgsConstructor
public class AcademicScheduleController {

    private final AcademicScheduleService academicScheduleService;

    @Operation(summary = "학사 일정 조회", description = "주어진 월에 해당하는 학사 일정을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            @ApiResponse(responseCode = "300", description = "잘못된 입력 값"),
            @ApiResponse(responseCode = "400", description = "서버 오류 발생")
    })
    @GetMapping("/monthly")
    public ApiResult<List<AcademicScheduleDto>> getMonthlySchedule(
            @Parameter(description = "조회할 월 (1~12)", required = true)
            @RequestParam int month) {
        try {
            List<AcademicScheduleDto> schedules = academicScheduleService.getMonthlySchedule(month);
            return ApiResult.ok(schedules);
        } catch (IllegalArgumentException e) {
            return ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, null);
        } catch (RuntimeException e) {
            return ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
