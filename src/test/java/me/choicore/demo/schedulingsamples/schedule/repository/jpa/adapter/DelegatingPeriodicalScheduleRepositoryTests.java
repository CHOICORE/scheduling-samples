package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support.TestComplexScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support.TestDelegatingPeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support.TestOnceScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support.TestWeeklyScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DelegatingPeriodicalScheduleRepositoryTests {
    private TestDelegatingPeriodicalScheduleRepository repository;

    @BeforeEach
    void setUp(
            @Autowired ScheduleJpaRepository scheduleJpaRepository,
            @Autowired TestComplexScheduleRepository complexScheduleRepository,
            @Autowired TestOnceScheduleRepository onceScheduleRepository,
            @Autowired TestWeeklyScheduleRepository weeklyScheduleRepository,
            @Autowired DailyScheduleRepository dailyScheduleRepository
    ) {
        repository = new TestDelegatingPeriodicalScheduleRepository(scheduleJpaRepository, List.of(complexScheduleRepository, onceScheduleRepository, weeklyScheduleRepository, dailyScheduleRepository));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void t1() {
        // given
        var schedule = new OnceSchedule(LocalDate.now());

        // when
        Long save = repository.save(schedule);

        // then
        assertThat(save).isNotNull().isGreaterThan(0L);
    }

    @Test
    void t2() {
        // given

        repository.save(new OnceSchedule(LocalDate.now()));
        repository.save(new WeeklySchedule(EnumSet.allOf(DayOfWeek.class)));
        // when
        var all = repository.findAll();

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    void t3() {
        // given
        repository.save(new OnceSchedule(LocalDate.now()));
        repository.save(new WeeklySchedule(EnumSet.allOf(DayOfWeek.class)));

        // when
        var all = repository.isScheduledFor(LocalDate.now());

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    void t4() {
        // given
        LocalDate date = LocalDate.of(2024, 1, 1);


        repository.save(new OnceSchedule(date));
        repository.save(new WeeklySchedule(EnumSet.of(date.getDayOfWeek())));
        // when
        var all = repository.isScheduledFor(LocalDate.now());

        // then
        assertThat(all).hasSize(0);
    }
}