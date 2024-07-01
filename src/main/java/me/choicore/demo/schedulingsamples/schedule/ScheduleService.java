package me.choicore.demo.schedulingsamples.schedule;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesFor(LocalDate date) {
        return scheduleRepository.isScheduledFor(date);
    }
}
