package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.entity.Univ;
import depth.mvp.thinkerbell.domain.notice.dto.AcademicScheduleDto;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicSchedule;
import depth.mvp.thinkerbell.global.exception.ErrorCode;
import depth.mvp.thinkerbell.global.exception.MapperException;

import java.time.LocalDate;

public class AcademicScheduleMapper {

    //entity to dto
    public static AcademicScheduleDto toDto(AcademicSchedule academicSchedule) {
        if (academicSchedule == null) {
            throw new MapperException(ErrorCode.INVALID_INPUT_VALUE);
        }

        String schedule = academicSchedule.getSchedule();

        LocalDate localDate[] = ScheduleParser.parseDate(schedule);

        return AcademicScheduleDto.builder()
                .id(academicSchedule.getId())
                .title(academicSchedule.getTitle())
                .startDate(localDate[0])
                .endDate(localDate[1])
                .univId(academicSchedule.getUniv().getId())
                .build();
    }

}
