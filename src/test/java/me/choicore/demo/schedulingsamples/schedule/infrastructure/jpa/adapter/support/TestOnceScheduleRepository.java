package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.support;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.OnceScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter.OnceScheduleRepository;
import org.springframework.stereotype.Component;

@Component
public class TestOnceScheduleRepository extends OnceScheduleRepository {
    private final OnceScheduleJpaRepository onceScheduleJpaRepository;

    public TestOnceScheduleRepository(OnceScheduleJpaRepository onceScheduleJpaRepository) {
        super(onceScheduleJpaRepository);
        this.onceScheduleJpaRepository = onceScheduleJpaRepository;
    }

    public void deleteAll() {
        onceScheduleJpaRepository.deleteAll();
    }
}
