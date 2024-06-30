package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.OnceScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DelegatingPeriodicalScheduleRepositoryTests {
    @Autowired
    private OnceScheduleJpaRepository onceScheduleJpaRepository;
    @Autowired
    private WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;
    @Autowired
    private PeriodicalScheduleRepository<Schedule> strategy;

    // TODO: 롤백 처리 리팩토링
    @AfterEach
    void tearDown() {
        onceScheduleJpaRepository.deleteAll();
        weeklyScheduleJpaRepository.deleteAll();
    }

    @Test
    void t1() {
        // given
        var schedule = new OnceSchedule(LocalDate.now());

        // when
        Long save = strategy.save(schedule);

        // then
        assertThat(save).isNotNull().isGreaterThan(0L);
    }

    @Test
    void t2() {
        // given

        strategy.save(new OnceSchedule(LocalDate.now()));
        strategy.save(new WeeklySchedule(EnumSet.allOf(DayOfWeek.class)));
        // when
        var all = strategy.findAll();

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    void t3() {
        // given
        strategy.save(new OnceSchedule(LocalDate.now()));
        strategy.save(new WeeklySchedule(EnumSet.allOf(DayOfWeek.class)));

        // when
        var all = strategy.isScheduledFor(LocalDate.now());

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    void t4() {
        // given
        LocalDate date = LocalDate.of(2024, 1, 1);


        strategy.save(new OnceSchedule(date));
        strategy.save(new WeeklySchedule(EnumSet.of(date.getDayOfWeek())));
        // when
        var all = strategy.isScheduledFor(LocalDate.now());

        // then
        assertThat(all).hasSize(0);
    }
}