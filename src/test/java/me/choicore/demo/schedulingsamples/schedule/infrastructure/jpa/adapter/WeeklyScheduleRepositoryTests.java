package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.support.TestWeeklyScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class WeeklyScheduleRepositoryTests {
    @Autowired
    private TestWeeklyScheduleRepository weeklyScheduleRepository;

    @AfterEach
    void tearDown() {
        weeklyScheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("주간 스케줄을 저장하고 조회한다.")
    void t1() {
        Set<DayOfWeek> daysOfWeek = EnumSet.allOf(DayOfWeek.class);
        long dummyScheduleId = 1L;
        var schedule = new ScheduleWrapper<>(dummyScheduleId, new WeeklySchedule(daysOfWeek));
        Long save = weeklyScheduleRepository.save(schedule);

        assertThat(save).isEqualTo(dummyScheduleId);

        var found = weeklyScheduleRepository.findAll();
        assertThat(found).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    @DisplayName("특정 요일에 스케줄이 등록되어 있는지 확인한다.")
    void t2(int dayOfWeek) {
        // given
        Set<DayOfWeek> daysOfWeek = EnumSet.allOf(DayOfWeek.class);
        long dummyScheduleId = 1L;
        var schedule = new ScheduleWrapper<>(dummyScheduleId, new WeeklySchedule(daysOfWeek));
        weeklyScheduleRepository.save(schedule);
        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.of(dayOfWeek)));

        // when & then
        weeklyScheduleRepository.isScheduledFor(targetDate)
                .forEach(it -> assertThat(it.schedule().isScheduledFor(targetDate)).isTrue());
    }

    @ParameterizedTest
    @DisplayName("특정 요일에 스케줄이 등록되어 있지 않은 경우 조회 결과가 없다.")
    @ValueSource(ints = {2, 4, 6, 7})
    void t3(int dayOfWeek) {
        // given
        Set<DayOfWeek> daysOfWeek = EnumSet.noneOf(DayOfWeek.class);
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        daysOfWeek.add(DayOfWeek.FRIDAY);

        long dummyScheduleId = 1L;
        var schedule = new ScheduleWrapper<>(dummyScheduleId, new WeeklySchedule(daysOfWeek));
        weeklyScheduleRepository.save(schedule);
        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.with(TemporalAdjusters.next(DayOfWeek.of(dayOfWeek)));

        // when & then
        assertThat(weeklyScheduleRepository.isScheduledFor(targetDate)).isEmpty();
    }
}