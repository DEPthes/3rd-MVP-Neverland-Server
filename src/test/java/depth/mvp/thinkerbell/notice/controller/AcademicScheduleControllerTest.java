package depth.mvp.thinkerbell.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import depth.mvp.thinkerbell.domain.notice.controller.AcademicScheduleController;
import depth.mvp.thinkerbell.domain.notice.dto.AcademicScheduleDto;
import depth.mvp.thinkerbell.domain.notice.service.AcademicScheduleService;
import depth.mvp.thinkerbell.global.dto.ApiResult;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcademicScheduleController.class)
public class AcademicScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademicScheduleService academicScheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMonthlySchedule_ValidMonth_ReturnsSchedule() throws Exception {
        // given
        AcademicScheduleDto scheduleDto = AcademicScheduleDto.builder()
                .id(2L)
                .title("hello")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 1, 1))
                .univId(null)
                .build();

        List<AcademicScheduleDto> schedules = Collections.singletonList(scheduleDto);

        given(academicScheduleService.getMonthlySchedule(anyInt())).willReturn(schedules);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/academic-schedule/monthly")
                .param("month", "1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ApiResult.ok(schedules))));
    }

    @Test
    void getMonthlySchedule_InvalidMonth_ReturnsBadRequest() throws Exception {
        //given
        given(academicScheduleService.getMonthlySchedule(anyInt()))
                .willThrow(new IllegalArgumentException("1월 부터 12월 사이의 값을 입력해주세요"));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/academic-schedule/monthly")
                .param("month", "13")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(content().json(objectMapper.writeValueAsString(ApiResult.withError(ErrorCode.INVALID_INPUT_VALUE, null))));
    }

    @Test
    void getMonthlySchedule_InternalServerError() throws Exception {
        // given
        given(academicScheduleService.getMonthlySchedule(anyInt())).willThrow(new RuntimeException("Internal server error"));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/academic-schedule/monthly")
                .param("month", "1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(content().json(objectMapper.writeValueAsString(ApiResult.withError(ErrorCode.INTERNAL_SERVER_ERROR, null))));
    }
}
