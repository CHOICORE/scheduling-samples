package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleStrategy;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.ScheduleJpaRepository;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ScheduleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CompositePeriodicalScheduleStrategy implements PeriodicalScheduleStrategy<Schedule> {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final Map<Class<? extends Schedule>, PeriodicalScheduleStrategy<? extends Schedule>> strategies = new HashMap<>();

    public CompositePeriodicalScheduleStrategy(
            ScheduleJpaRepository scheduleJpaRepository,
            List<PeriodicalScheduleStrategy<? extends Schedule>> strategies
    ) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        strategies.forEach(strategy -> this.strategies.put(getScheduleClass(strategy), strategy));
    }

    private <T extends Schedule> Class<T> getScheduleClass(PeriodicalScheduleStrategy<T> strategy) {
        return strategy.getSuggestedClass();
    }

    @Override
    public Long save(Long id, Schedule schedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public Long save(Schedule schedule) {
        ScheduleEntity saved = scheduleJpaRepository.save(ScheduleEntity.create(schedule));
        Long scheduleId = saved.getId();
        PeriodicalScheduleStrategy<Schedule> strategy = getStrategy(schedule);
        strategy.save(scheduleId, schedule);
        return scheduleId;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Schedule> findAll() {
        List<CompletableFuture<List<? extends Schedule>>> futures = new ArrayList<>();
        for (PeriodicalScheduleStrategy<? extends Schedule> strategy : strategies.values()) {
            CompletableFuture<List<? extends Schedule>> future = CompletableFuture.supplyAsync(strategy::findAll);
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return (List<Schedule>) allOf.thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .join();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Schedule> isScheduledFor(LocalDate date) {
        List<CompletableFuture<List<? extends Schedule>>> futures = new ArrayList<>();
        for (PeriodicalScheduleStrategy<? extends Schedule> strategy : strategies.values()) {
            CompletableFuture<List<? extends Schedule>> future = CompletableFuture.supplyAsync(() -> strategy.isScheduledFor(date));
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return (List<Schedule>) allOf.thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .join();
    }

    @Override
    public Class<Schedule> getSuggestedClass() {
        return Schedule.class;
    }

    @SuppressWarnings("unchecked")
    private <T extends Schedule> PeriodicalScheduleStrategy<T> getStrategy(T schedule) {
        return (PeriodicalScheduleStrategy<T>) strategies.get(schedule.getClass());
    }
}
