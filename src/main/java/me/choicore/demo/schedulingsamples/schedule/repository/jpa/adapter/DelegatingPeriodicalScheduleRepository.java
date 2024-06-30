package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.ScheduleWrapper;
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
public class DelegatingPeriodicalScheduleRepository implements PeriodicalScheduleRepository<Schedule> {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final Map<Class<? extends Schedule>, PeriodicalScheduleRepository<? extends Schedule>> delegates = new HashMap<>();

    public DelegatingPeriodicalScheduleRepository(
            ScheduleJpaRepository scheduleJpaRepository,
            List<PeriodicalScheduleRepository<? extends Schedule>> delegates
    ) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        delegates.forEach(strategy -> this.delegates.put(strategy.getScheduleType(), strategy));
    }

    @SuppressWarnings("unchecked")
    private <T extends Schedule> PeriodicalScheduleRepository<T> getRepository(T schedule) {
        return (PeriodicalScheduleRepository<T>) delegates.get(schedule.getClass());
    }

    @Override
    @Transactional
    public Long save(Schedule schedule) {
        ScheduleEntity saved = scheduleJpaRepository.save(ScheduleEntity.create(schedule));
        return delegate(saved.getId(), schedule);

    }

    private Long delegate(Long id, Schedule schedule) {
        return getRepository(schedule).save(new ScheduleWrapper<>(id, schedule));
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Schedule> findAll() {
        List<CompletableFuture<List<? extends Schedule>>> futures = new ArrayList<>();
        for (PeriodicalScheduleRepository<? extends Schedule> strategy : delegates.values()) {
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
        for (PeriodicalScheduleRepository<? extends Schedule> strategy : delegates.values()) {
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

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Schedule> Class<C> getScheduleType() {
        return (Class<C>) Schedule.class;
    }
}
