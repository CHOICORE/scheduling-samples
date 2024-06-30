package me.choicore.demo.schedulingsamples.schedule;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.OnceScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DelegatingScheduleRepositoryTests {
    @Autowired
    private DelegatingScheduleRepository repository;
    @Autowired
    private OnceScheduleJpaRepository onceScheduleJpaRepository;
    @Autowired
    private WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    // TODO: 롤백 처리 리팩토링
    @AfterEach
    void tearDown() {
        onceScheduleJpaRepository.deleteAll();
        weeklyScheduleJpaRepository.deleteAll();
    }

    @Test
    void t1() {
        var schedule = new OnceSchedule(LocalDate.now());

        Long savedId = repository.save(schedule);

        Assertions.assertThat(savedId).isNotNull().isGreaterThan(0L);
    }

    @Test
    void t2() {
        var onceSchedule = new OnceSchedule(LocalDate.now());
        var weeklySchedule = new WeeklySchedule(EnumSet.allOf(DayOfWeek.class));
        repository.save(onceSchedule);
        repository.save(weeklySchedule);

        List<Schedule> all = repository.findAll();
        Assertions.assertThat(all).hasSize(2);
    }

    @Test
    void t3() {
        var onceSchedule = new OnceSchedule(LocalDate.now());
        var weeklySchedule = new WeeklySchedule(EnumSet.allOf(DayOfWeek.class));
        repository.save(onceSchedule);
        repository.save(weeklySchedule);

        List<Schedule> all = repository.isScheduledFor(LocalDate.now());
        for (Schedule schedule : all) {
            System.out.println(schedule);
        }
        Assertions.assertThat(all).hasSize(2);
    }
}