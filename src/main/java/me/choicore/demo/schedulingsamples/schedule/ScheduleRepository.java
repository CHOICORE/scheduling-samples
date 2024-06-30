package me.choicore.demo.schedulingsamples.schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    Long save(Schedule schedule);

    List<Schedule> findAll();

    List<Schedule> isScheduledFor(LocalDate date);
}
