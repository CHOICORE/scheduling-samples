package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.DailySchedule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyScheduleRepository implements PeriodicalScheduleRepository<ScheduleWrapper<DailySchedule>> {
    private static final DailySchedule INSTANCE = new DailySchedule();
    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public Long save(ScheduleWrapper<DailySchedule> schedule) {
        return schedule.id();
    }

    @Override
    public List<ScheduleWrapper<DailySchedule>> findAll() {
        List<ScheduleEntity> found = scheduleJpaRepository.findByPeriodicity(INSTANCE.getPeriodicity());
        return found.stream().map(
                it -> new ScheduleWrapper<>(it.getId(), INSTANCE)
        ).toList();
    }

    @Override
    public List<ScheduleWrapper<DailySchedule>> isScheduledFor(LocalDate date) {
        return List.of();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Schedule> Class<C> getScheduleType() {
        return (Class<C>) DailySchedule.class;
    }
}
