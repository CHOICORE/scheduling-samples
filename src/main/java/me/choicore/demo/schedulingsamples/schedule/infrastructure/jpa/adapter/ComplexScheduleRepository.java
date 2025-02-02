package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.adapter;


import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.domain.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;
import me.choicore.demo.schedulingsamples.schedule.domain.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.domain.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.WeekOfMonth;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.ComplexScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.ComplexScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.WeeklyScheduleEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComplexScheduleRepository implements PeriodicalScheduleRepository<ScheduleWrapper<ComplexSchedule>> {
    private final ComplexScheduleJpaRepository complexScheduleJpaRepository;
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    private Map<Long, List<ComplexScheduleEntity>> getScheduleByScheduleId(List<ComplexScheduleEntity> all) {
        return all.stream().collect(Collectors.groupingBy(ComplexScheduleEntity::getScheduleId));
    }

    private Set<Long> getWeeklyScheduleIds(List<ComplexScheduleEntity> entities) {
        return entities.stream().map(ComplexScheduleEntity::getWeeklyScheduleId).collect(Collectors.toSet());
    }

    private List<ScheduleWrapper<ComplexSchedule>> getComplexSchedules(Map<Long, List<ComplexScheduleEntity>> scheduleByScheduleId, Map<Long, WeeklyScheduleEntity> grouped) {
        return scheduleByScheduleId.entrySet().stream().map(
                it -> {
                    Set<WeekOfMonth> weeksOfMonth = new LinkedHashSet<>();
                    List<ComplexScheduleEntity> complexSchedules = it.getValue();
                    for (ComplexScheduleEntity entities : complexSchedules) {
                        int scheduledMonth = entities.getScheduledMonth();
                        int scheduledWeekOfMonth = entities.getScheduledWeekOfMonth();
                        WeekOfMonth month = WeekOfMonth.of(Month.of(scheduledMonth), Week.valueOf(scheduledWeekOfMonth));
                        weeksOfMonth.add(month);
                    }

                    return new ScheduleWrapper<>(it.getKey(), new ComplexSchedule(weeksOfMonth, grouped.get(complexSchedules.getFirst().getWeeklyScheduleId()).toWeeklySchedule()));
                }
        ).toList();
    }


    @Override
    public Long save(ScheduleWrapper<ComplexSchedule> schedule) {
        WeeklyScheduleEntity weeklyScheduleEntity = weeklyScheduleJpaRepository
                .save(WeeklyScheduleEntity.specificOf(schedule.id(), schedule.schedule().weeklySchedule()));
        List<ComplexScheduleEntity> entities = ComplexScheduleEntity.create(schedule.id(), weeklyScheduleEntity.getId(), schedule.schedule());
        complexScheduleJpaRepository.saveAll(entities);
        return schedule.id();
    }

    @Override
    public List<ScheduleWrapper<ComplexSchedule>> findAll() {
        var all = complexScheduleJpaRepository.findAll();
        var weeklyScheduleIds = getWeeklyScheduleIds(all);
        var scheduleByScheduleId = getScheduleByScheduleId(all);
        var grouped = getWeeklySchedulesByIds(weeklyScheduleIds);

        return getComplexSchedules(scheduleByScheduleId, grouped);
    }

    private Map<Long, WeeklyScheduleEntity> getWeeklySchedulesByIds(Set<Long> weeklyScheduleIds) {
        return weeklyScheduleJpaRepository.findAllById(weeklyScheduleIds).stream().collect(Collectors.toMap(WeeklyScheduleEntity::getId, Function.identity()));
    }

    @Override
    public List<ScheduleWrapper<ComplexSchedule>> isScheduledFor(LocalDate date) {
        WeekOfMonth weekOfMonth = WeekOfMonth.of(date);
        var entities = complexScheduleJpaRepository.findByScheduledMonthAndScheduledWeekOfMonth(weekOfMonth.month().getValue(), weekOfMonth.week().numberOfWeek());
        if (weekOfMonth.isLastWeek(date)) {
            entities.addAll(complexScheduleJpaRepository.findByScheduledMonthAndScheduledWeekOfMonth(weekOfMonth.month().getValue(), Week.LAST.numberOfWeek()));
        }
        var weeklyScheduleIds = getWeeklyScheduleIds(entities);
        var scheduleByScheduleId = getScheduleByScheduleId(entities);
        var weeklySchedulesByIds = getWeeklySchedulesByIds(weeklyScheduleIds);
        var complexSchedules = getComplexSchedules(scheduleByScheduleId, weeklySchedulesByIds);

        return complexSchedules.stream().filter(it -> it.isScheduledFor(date)).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Schedule> Class<C> getScheduleType() {
        return (Class<C>) ComplexSchedule.class;
    }
}
