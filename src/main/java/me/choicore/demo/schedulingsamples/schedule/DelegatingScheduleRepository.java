package me.choicore.demo.schedulingsamples.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DelegatingScheduleRepository implements ScheduleRepository {
    private final PeriodicalScheduleRepository<Schedule> periodicalScheduleRepository;

    @Override
    @Transactional
    public Long save(Schedule schedule) {
        return periodicalScheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return periodicalScheduleRepository.findAll();
    }

    @Override
    public List<Schedule> isScheduledFor(LocalDate date) {
        return periodicalScheduleRepository.isScheduledFor(date);
    }
}
