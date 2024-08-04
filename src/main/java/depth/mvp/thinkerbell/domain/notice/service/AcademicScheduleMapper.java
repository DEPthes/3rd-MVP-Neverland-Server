package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.entity.Univ;
import depth.mvp.thinkerbell.domain.notice.dto.AcademicScheduleDto;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicSchedule;

public class AcademicScheduleMapper {

    //entity to dto
    public static AcademicScheduleDto toDto(AcademicSchedule academicSchedule) {
        return AcademicScheduleDto.builder()
                .id(academicSchedule.getId())
                .title(academicSchedule.getTitle())
                .schedule(academicSchedule.getSchedule())
                .univId(academicSchedule.getUniv().getId())
                .build();
    }

    //dto to entity
    public static AcademicSchedule toEntity(AcademicScheduleDto academicScheduleDto, Univ univ) {
        return AcademicSchedule.builder()
                .title(academicScheduleDto.getSchedule())
                .schedule(academicScheduleDto.getSchedule())
                .univ(univ)
                .build();
    }
}
