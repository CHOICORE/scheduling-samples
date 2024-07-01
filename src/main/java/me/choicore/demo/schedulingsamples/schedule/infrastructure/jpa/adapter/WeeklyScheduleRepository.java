package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.domain.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;
import me.choicore.demo.schedulingsamples.schedule.domain.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.domain.type.WeeklySchedule;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.WeeklyScheduleEntity;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class WeeklyScheduleRepository implements PeriodicalScheduleRepository<ScheduleWrapper<WeeklySchedule>> {
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    @Override
    public Long save(ScheduleWrapper<WeeklySchedule> schedule) {
        weeklyScheduleJpaRepository.save(WeeklyScheduleEntity.create(schedule.id(), schedule.schedule()));
        return schedule.id();
    }

    @Override
    public List<ScheduleWrapper<WeeklySchedule>> findAll() {
        return weeklyScheduleJpaRepository
                .findAll()
                .stream()
                .map(it -> new ScheduleWrapper<>(it.getScheduleId(), it.toWeeklySchedule()))
                .toList();
    }

    @Override
    public List<ScheduleWrapper<WeeklySchedule>> isScheduledFor(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        var schedules = switch (dayOfWeek) {
            case MONDAY -> weeklyScheduleJpaRepository.findByPeriodicityAndScheduledMonday(Periodicity.WEEKLY, true);
            case TUESDAY -> weeklyScheduleJpaRepository.findByPeriodicityAndScheduledTuesday(Periodicity.WEEKLY, true);
            case WEDNESDAY ->
                    weeklyScheduleJpaRepository.findByPeriodicityAndScheduledWednesday(Periodicity.WEEKLY, true);
            case THURSDAY ->
                    weeklyScheduleJpaRepository.findByPeriodicityAndScheduledThursday(Periodicity.WEEKLY, true);
            case FRIDAY -> weeklyScheduleJpaRepository.findByPeriodicityAndScheduledFriday(Periodicity.WEEKLY, true);
            case SATURDAY ->
                    weeklyScheduleJpaRepository.findByPeriodicityAndScheduledSaturday(Periodicity.WEEKLY, true);
            case SUNDAY -> weeklyScheduleJpaRepository.findByPeriodicityAndScheduledSunday(Periodicity.WEEKLY, true);
        };

        return schedules.stream().map(it -> new ScheduleWrapper<>(it.getScheduleId(), it.toWeeklySchedule())).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Schedule> Class<C> getScheduleType() {
        return (Class<C>) WeeklySchedule.class;
    }
}
