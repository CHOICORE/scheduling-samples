package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleStrategy;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.WeeklyScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeeklyScheduleRepository implements PeriodicalScheduleStrategy<WeeklySchedule> {
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    @Override
    public Long save(Long id, WeeklySchedule schedule) {
        WeeklyScheduleEntity weeklyScheduleEntity = WeeklyScheduleEntity.create(id, schedule);
        WeeklyScheduleEntity saved = weeklyScheduleJpaRepository.save(weeklyScheduleEntity);
        return saved.getId();
    }

    @Override
    public Long save(WeeklySchedule schedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleJpaRepository
                .findAll()
                .stream()
                .map(WeeklyScheduleEntity::toWeeklySchedule).collect(Collectors.toList());
    }

    @Override
    public List<WeeklySchedule> isScheduledFor(LocalDate date) {
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

        return schedules.stream().map(WeeklyScheduleEntity::toWeeklySchedule).collect(Collectors.toList());
    }

    @Override
    public Class<WeeklySchedule> getSuggestedClass() {
        return WeeklySchedule.class;
    }


}
