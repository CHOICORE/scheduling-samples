package me.choicore.demo.schedulingsamples.schedule;

import java.util.List;

public interface ScheduleRepository {
    List<Schedule> findAll();
}
