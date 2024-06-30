package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleStrategy;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.OnceScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.OnceScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OnceScheduleRepository implements PeriodicalScheduleStrategy<OnceSchedule> {
    private final OnceScheduleJpaRepository onceScheduleJpaRepository;

    @Override
    public Long save(Long id, OnceSchedule schedule) {
        OnceScheduleEntity onceScheduleEntity = OnceScheduleEntity.create(id, schedule);
        return onceScheduleJpaRepository.save(onceScheduleEntity).getId();
    }

    @Override
    public Long save(OnceSchedule schedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<OnceSchedule> findAll() {
        List<OnceScheduleEntity> onceEntities = onceScheduleJpaRepository.findAll();
        return onceEntities.stream().map(OnceScheduleEntity::toOnceSchedule).collect(Collectors.toList());
    }

    @Override
    public List<OnceSchedule> isScheduledFor(LocalDate date) {
        List<OnceScheduleEntity> onceEntities = onceScheduleJpaRepository.findByDate(date);
        return onceEntities.stream().map(OnceScheduleEntity::toOnceSchedule).collect(Collectors.toList());
    }

    @Override
    public Class<OnceSchedule> getSuggestedClass() {
        return OnceSchedule.class;
    }
}
