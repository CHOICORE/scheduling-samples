package me.choicore.demo.schedulingsamples.schedule.domain.type;


import jakarta.annotation.Nullable;
import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;

import java.time.LocalDate;

public class DailySchedule implements Schedule {
    @Override
    public boolean isScheduledFor(@Nullable LocalDate date) {
        return true;
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.DAILY;
    }
}
