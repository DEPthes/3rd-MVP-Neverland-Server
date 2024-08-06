package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.AcademicScheduleDto;
import depth.mvp.thinkerbell.domain.notice.entity.AcademicSchedule;
import depth.mvp.thinkerbell.domain.notice.repository.AcademicScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AcademicScheduleService {

    private final AcademicScheduleRepository academicScheduleRepository;

    public List<AcademicScheduleDto> getMonthlySchedule(int month) {

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("1월 부터 12월 사이의 값을 입력해주세요");
        }

        try {
            List<AcademicSchedule> schedules = academicScheduleRepository.findAll();

            return schedules.stream()
                    .map(AcademicScheduleMapper::toDto)
                    .filter(academicScheduleDto -> {
                        LocalDate localDate = academicScheduleDto.getStartDate();
                        return localDate != null && localDate.getMonthValue() == month;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
