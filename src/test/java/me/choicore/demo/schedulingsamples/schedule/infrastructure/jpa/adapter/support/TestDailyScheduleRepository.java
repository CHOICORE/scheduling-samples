package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.ScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.DailyScheduleRepository;

public class TestDailyScheduleRepository extends DailyScheduleRepository {
    private final ScheduleJpaRepository scheduleJpaRepository;

    public TestDailyScheduleRepository(ScheduleJpaRepository scheduleJpaRepository, ScheduleJpaRepository scheduleJpaRepository1) {
        super(scheduleJpaRepository);
        this.scheduleJpaRepository = scheduleJpaRepository1;
    }

    public void deleteAll() {
        scheduleJpaRepository.deleteAll();
    }
}
