package me.choicore.demo.schedulingsamples.schedule.type;

import jakarta.annotation.Nullable;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;

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
