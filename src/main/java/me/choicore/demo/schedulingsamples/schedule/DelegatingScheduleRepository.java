package me.choicore.demo.schedulingsamples.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DelegatingScheduleRepository implements ScheduleRepository {
    private final PeriodicalScheduleStrategy<Schedule> periodicalScheduleStrategy;

    @Override
    @Transactional
    public Long save(Schedule schedule) {
        return periodicalScheduleStrategy.save(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return periodicalScheduleStrategy.findAll();
    }

    @Override
    public List<Schedule> isScheduledFor(LocalDate date) {
        return periodicalScheduleStrategy.isScheduledFor(date);
    }
}
