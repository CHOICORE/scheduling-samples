package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.ComplexScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.WeeklyScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.unit.WeekOfMonth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ComplexScheduleJpaRepositoryTests {
    @Autowired
    private ComplexScheduleJpaRepository complexScheduleJpaRepository;
    @Autowired
    private WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    @Test
    void t1() {
        // given
        long dummyScheduleId = 1L;

        Set<WeekOfMonth> weekOfMonths = Set.of(
                WeekOfMonth.of(Month.of(1), Week.SECOND),
                WeekOfMonth.of(Month.of(2), Week.SECOND),
                WeekOfMonth.of(Month.of(3), Week.SECOND)
        );
        var schedule = new ComplexSchedule(weekOfMonths, EnumSet.allOf(DayOfWeek.class));

        WeeklyScheduleEntity weeklyScheduleEntity = weeklyScheduleJpaRepository
                .save(WeeklyScheduleEntity.specificOf(dummyScheduleId, schedule.weeklySchedule()));

        List<ComplexScheduleEntity> entities = ComplexScheduleEntity.create(dummyScheduleId, weeklyScheduleEntity.getId(), schedule);

        // when
        List<ComplexScheduleEntity> saved = complexScheduleJpaRepository.saveAll(entities);

        // then
        assertThat(saved).hasSize(weekOfMonths.size());
        assertThat(saved).allMatch(entity ->
                entity.getId() != null
                        && entity.getId() > 0L
                        && entity.getWeeklyScheduleId().equals(weeklyScheduleEntity.getId())
        );
    }
}