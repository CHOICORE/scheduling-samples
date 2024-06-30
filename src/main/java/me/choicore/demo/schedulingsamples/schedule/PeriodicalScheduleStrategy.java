package me.choicore.demo.schedulingsamples.schedule;

import java.time.LocalDate;
import java.util.List;

public interface PeriodicalScheduleStrategy<T extends Schedule> {
    Long save(Long id, T schedule);

    Long save(T schedule);

    List<T> findAll();

    List<T> isScheduledFor(LocalDate date);

    Class<T> getSuggestedClass();
}