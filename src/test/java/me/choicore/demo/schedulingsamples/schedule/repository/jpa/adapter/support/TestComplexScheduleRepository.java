package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ComplexScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.ComplexScheduleRepository;
import org.springframework.stereotype.Component;

@Component
public class TestComplexScheduleRepository extends ComplexScheduleRepository {
    private final ComplexScheduleJpaRepository complexScheduleJpaRepository;
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    public TestComplexScheduleRepository(ComplexScheduleJpaRepository complexScheduleJpaRepository, WeeklyScheduleJpaRepository weeklyScheduleJpaRepository) {
        super(complexScheduleJpaRepository, weeklyScheduleJpaRepository);
        this.complexScheduleJpaRepository = complexScheduleJpaRepository;
        this.weeklyScheduleJpaRepository = weeklyScheduleJpaRepository;
    }

    public void deleteAll() {
        weeklyScheduleJpaRepository.deleteAll();
        complexScheduleJpaRepository.deleteAll();
    }
}
