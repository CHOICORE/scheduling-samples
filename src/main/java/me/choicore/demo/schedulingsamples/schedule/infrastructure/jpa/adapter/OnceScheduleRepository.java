package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.OnceScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.OnceScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OnceScheduleRepository implements PeriodicalScheduleRepository<ScheduleWrapper<OnceSchedule>> {
    private final OnceScheduleJpaRepository onceScheduleJpaRepository;

    @Override
    public Long save(ScheduleWrapper<OnceSchedule> schedule) {
        onceScheduleJpaRepository.save(OnceScheduleEntity.create(schedule.id(), schedule.schedule()));
        return schedule.id();
    }

    @Override
    public List<ScheduleWrapper<OnceSchedule>> findAll() {
        List<OnceScheduleEntity> entities = onceScheduleJpaRepository.findAll();
        return entities.stream().map(
                it -> new ScheduleWrapper<>(it.getScheduleId(), it.toOnceSchedule())
        ).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleWrapper<OnceSchedule>> isScheduledFor(LocalDate date) {
        List<OnceScheduleEntity> entities = onceScheduleJpaRepository.findByDate(date);
        return entities.stream().map(
                it -> new ScheduleWrapper<>(it.getScheduleId(), it.toOnceSchedule())
        ).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Schedule> Class<C> getScheduleType() {
        return (Class<C>) OnceSchedule.class;
    }
}
