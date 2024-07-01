package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.WeeklyScheduleRepository;
import org.springframework.stereotype.Component;

@Component
public class TestWeeklyScheduleRepository extends WeeklyScheduleRepository {
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    public TestWeeklyScheduleRepository(WeeklyScheduleJpaRepository weeklyScheduleJpaRepository, WeeklyScheduleJpaRepository weeklyScheduleJpaRepository1) {
        super(weeklyScheduleJpaRepository);
        this.weeklyScheduleJpaRepository = weeklyScheduleJpaRepository1;
    }

    public void deleteAll() {
        weeklyScheduleJpaRepository.deleteAll();
    }
}
