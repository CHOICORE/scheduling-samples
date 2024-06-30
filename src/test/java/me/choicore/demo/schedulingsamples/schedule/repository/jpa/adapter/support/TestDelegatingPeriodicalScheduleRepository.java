package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.DelegatingPeriodicalScheduleRepository;

import java.util.List;

public class TestDelegatingPeriodicalScheduleRepository extends DelegatingPeriodicalScheduleRepository {
    private final List<PeriodicalScheduleRepository<? extends Schedule>> delegates;

    public TestDelegatingPeriodicalScheduleRepository(ScheduleJpaRepository scheduleJpaRepository, List<PeriodicalScheduleRepository<? extends Schedule>> delegates) {
        super(scheduleJpaRepository, delegates);
        this.delegates = delegates;
    }

    public void deleteAll() {
        for (PeriodicalScheduleRepository<? extends Schedule> delegate : delegates) {
            switch (delegate) {
                case TestComplexScheduleRepository testComplexScheduleRepository ->
                        testComplexScheduleRepository.deleteAll();
                case TestWeeklyScheduleRepository testWeeklyScheduleRepository ->
                        testWeeklyScheduleRepository.deleteAll();
                case TestOnceScheduleRepository testOnceScheduleRepository -> testOnceScheduleRepository.deleteAll();
                case TestDailyScheduleRepository testDailyScheduleRepository -> testDailyScheduleRepository.deleteAll();
                default -> {
                }
            }
        }
    }
}
