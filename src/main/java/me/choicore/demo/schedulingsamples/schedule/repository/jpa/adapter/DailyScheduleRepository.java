package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.PeriodicalScheduleStrategy;
import me.choicore.demo.schedulingsamples.schedule.type.DailySchedule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyScheduleRepository implements PeriodicalScheduleStrategy<DailySchedule> {
    private static final DailySchedule INSTANCE = new DailySchedule();

    @Override
    public Long save(Long id, DailySchedule schedule) {
        return id;
    }

    @Override
    public Long save(DailySchedule schedule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DailySchedule> findAll() {
        return List.of(INSTANCE);
    }

    @Override
    public List<DailySchedule> isScheduledFor(LocalDate date) {
        return List.of(INSTANCE);
    }

    @Override
    public Class<DailySchedule> getSuggestedClass() {
        return DailySchedule.class;
    }
}
