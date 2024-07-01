package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.domain.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.domain.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.WeekOfMonth;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.support.TestComplexScheduleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ComplexScheduleRepositoryTests {
    @Autowired
    private TestComplexScheduleRepository complexScheduleRepository;

    @AfterEach
    void tearDown() {
        complexScheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("복합 스케줄을 저장하고 조회한다.")
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
        complexScheduleRepository.save(new ScheduleWrapper<>(dummyScheduleId, schedule));

        // then
        var found = complexScheduleRepository.findAll();
        assertThat(found).hasSize(1);
    }

    @Test
    @DisplayName("복합 스케줄을 여러 개 저장하고 조회한다.")
    void t2() {
        // given
        Set<WeekOfMonth> weekOfMonths = Set.of(
                WeekOfMonth.of(Month.of(1), Week.SECOND),
                WeekOfMonth.of(Month.of(2), Week.SECOND),
                WeekOfMonth.of(Month.of(3), Week.SECOND)
        );
        var schedule = new ComplexSchedule(weekOfMonths, EnumSet.allOf(DayOfWeek.class));

        // when
        complexScheduleRepository.save(new ScheduleWrapper<>(1L, schedule));
        complexScheduleRepository.save(new ScheduleWrapper<>(2L, schedule));

        // then
        var found = complexScheduleRepository.findAll();
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("특정 날짜에 스케줄이 등록되어 있는지 확인한다.")
    void t3() {
        // given
        LocalDate date = LocalDate.of(2024, 6, 24);
        complexScheduleRepository.save(new ScheduleWrapper<>(1L, new ComplexSchedule(Set.of(WeekOfMonth.of(Month.of(6), Week.LAST)), EnumSet.allOf(DayOfWeek.class))));
        complexScheduleRepository.save(new ScheduleWrapper<>(2L, new ComplexSchedule(Set.of(WeekOfMonth.of(date)), EnumSet.allOf(DayOfWeek.class))));

        // when
        var found = complexScheduleRepository.isScheduledFor(date);

        // then
        assertThat(found).hasSize(2);
        found.forEach(it -> assertThat(it.schedule().isScheduledFor(date)).isTrue());
    }
}