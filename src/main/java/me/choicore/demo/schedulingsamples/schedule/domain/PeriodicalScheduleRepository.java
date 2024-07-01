package me.choicore.demo.schedulingsamples.schedule.domain;

import java.time.LocalDate;
import java.util.List;

public interface PeriodicalScheduleRepository<T extends Schedule> {
    Long save(T schedule);

    List<T> findAll();

    List<T> isScheduledFor(LocalDate date);

    <C extends Schedule> Class<C> getScheduleType();
}