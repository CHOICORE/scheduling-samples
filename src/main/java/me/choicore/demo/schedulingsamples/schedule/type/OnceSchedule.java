package me.choicore.demo.schedulingsamples.schedule.type;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;

import java.time.LocalDate;

public class OnceSchedule implements Schedule {
    /**
     * 날짜
     */
    private final LocalDate date;

    public OnceSchedule(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    @Override
    public boolean isScheduledFor(@Nonnull LocalDate date) {
        return this.date.equals(date);
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.ONCE;
    }
}
