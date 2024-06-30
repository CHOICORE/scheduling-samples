package me.choicore.demo.schedulingsamples.schedule.type;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;

import java.time.LocalDate;

/**
 * @param date 날짜
 */
public record OnceSchedule(LocalDate date) implements Schedule {
    public OnceSchedule {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
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
