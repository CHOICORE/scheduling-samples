package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.unit.WeekOfMonth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ComplexScheduleRepositoryTests {
    @Autowired
    private ComplexScheduleRepository complexScheduleRepository;

    @Test
    void t1() {
        // given
        Set<WeekOfMonth> weekOfMonths = Set.of(
                WeekOfMonth.of(Month.of(1), Week.SECOND),
                WeekOfMonth.of(Month.of(2), Week.SECOND),
                WeekOfMonth.of(Month.of(3), Week.SECOND)
        );
        var schedule = new ComplexSchedule(weekOfMonths, EnumSet.allOf(DayOfWeek.class));
        long dummyScheduleId = 1L;

        // when
        complexScheduleRepository.save(dummyScheduleId, schedule);

        // then
        List<ComplexSchedule> found = complexScheduleRepository.findAll();
        assertThat(found).hasSize(1);
    }

    @Test
    void t2() {
        // given
        Set<WeekOfMonth> weekOfMonths = Set.of(
                WeekOfMonth.of(Month.of(1), Week.SECOND),
                WeekOfMonth.of(Month.of(2), Week.SECOND),
                WeekOfMonth.of(Month.of(3), Week.SECOND)
        );
        var schedule = new ComplexSchedule(weekOfMonths, EnumSet.allOf(DayOfWeek.class));

        // when
        complexScheduleRepository.save(1L, schedule);
        complexScheduleRepository.save(2L, schedule);

        // then
        List<ComplexSchedule> found = complexScheduleRepository.findAll();
        assertThat(found).hasSize(2);
    }

    @Test
    void t3() {
        // given
        LocalDate date = LocalDate.of(2024, 6, 24);
        complexScheduleRepository.save(1L, new ComplexSchedule(Set.of(WeekOfMonth.of(Month.of(6), Week.LAST)), EnumSet.allOf(DayOfWeek.class)));
        complexScheduleRepository.save(2L, new ComplexSchedule(Set.of(WeekOfMonth.of(date)), EnumSet.allOf(DayOfWeek.class)));

        // when
        List<ComplexSchedule> found = complexScheduleRepository.isScheduledFor(date);

        // then
        assertThat(found).hasSize(2);
    }
}