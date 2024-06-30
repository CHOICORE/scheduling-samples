package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import lombok.RequiredArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleStrategy;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ComplexScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.WeeklyScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ComplexScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.WeeklyScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.unit.WeekOfMonth;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class ComplexScheduleRepository implements PeriodicalScheduleStrategy<ComplexSchedule> {
    private final ComplexScheduleJpaRepository complexScheduleJpaRepository;
    private final WeeklyScheduleJpaRepository weeklyScheduleJpaRepository;

    private Map<Long, List<ComplexScheduleEntity>> getScheduleByScheduleId(List<ComplexScheduleEntity> all) {
        return all.stream().collect(Collectors.groupingBy(ComplexScheduleEntity::getScheduleId));
    }

    private Set<Long> getWeeklyScheduleIds(List<ComplexScheduleEntity> entities) {
        return entities.stream().map(ComplexScheduleEntity::getWeeklyScheduleId).collect(Collectors.toSet());
    }

    private List<ComplexSchedule> getComplexSchedules(Map<Long, List<ComplexScheduleEntity>> scheduleByScheduleId, Map<Long, WeeklyScheduleEntity> grouped) {
        return scheduleByScheduleId.values().stream().map(
                it -> {
                    Set<WeekOfMonth> weeksOfMonth = new LinkedHashSet<>();
                    for (ComplexScheduleEntity entities : it) {
                        int scheduledMonth = entities.getScheduledMonth();
                        int scheduledWeekOfMonth = entities.getScheduledWeekOfMonth();
                        WeekOfMonth month = WeekOfMonth.of(Month.of(scheduledMonth), Week.valueOf(scheduledWeekOfMonth));
                        weeksOfMonth.add(month);
                    }

                    return new ComplexSchedule(weeksOfMonth, grouped.get(it.getFirst().getWeeklyScheduleId()).toWeeklySchedule());
                }
        ).toList();
    }

    @Override
    @Transactional
    public Long save(Long id, ComplexSchedule schedule) {
        WeeklyScheduleEntity weeklyScheduleEntity = weeklyScheduleJpaRepository
                .save(WeeklyScheduleEntity.specificOf(id, schedule.weeklySchedule()));
        List<ComplexScheduleEntity> entities = ComplexScheduleEntity.create(id, weeklyScheduleEntity.getScheduleId(), schedule);
        complexScheduleJpaRepository.saveAll(entities);
        return id;
    }

    @Override
    public Long save(ComplexSchedule schedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ComplexSchedule> findAll() {
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
    public List<ComplexSchedule> isScheduledFor(LocalDate date) {
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
    public Class<ComplexSchedule> getSuggestedClass() {
        return ComplexSchedule.class;
    }
}
