package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.WeeklyScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.util.EnumSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class WeeklyScheduleJpaRepositoryTests {
    @Autowired
    private WeeklyScheduleJpaRepository repository;

    @Test
    @DisplayName("스케줄 저장을 성공한다.")
    void t1() {
        // given
        var schedule = new WeeklySchedule(EnumSet.allOf(DayOfWeek.class));
        long dummyScheduleId = 1L;

        // when
        WeeklyScheduleEntity saved = repository.save(WeeklyScheduleEntity.create(dummyScheduleId, schedule));

        // then
        assertThat(saved.getId()).isNotNull().isGreaterThan(0);
    }

    @Test
    void t2() {
        // given
        var schedule = new WeeklySchedule(EnumSet.allOf(DayOfWeek.class));
        long dummyScheduleId = 1L;
        WeeklyScheduleEntity saved = repository.save(WeeklyScheduleEntity.create(dummyScheduleId, schedule));

        // when
        WeeklyScheduleEntity found = repository.findById(saved.getId()).orElseThrow();

        // then
        WeeklySchedule weeklySchedule = found.toWeeklySchedule();
        assertThat(weeklySchedule.daysOfWeek()).isEqualTo(schedule.daysOfWeek());
    }
}