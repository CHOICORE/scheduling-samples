package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.WeeklyScheduleRepository;
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
