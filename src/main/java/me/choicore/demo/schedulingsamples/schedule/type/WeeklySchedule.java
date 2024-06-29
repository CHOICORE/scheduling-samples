package me.choicore.demo.schedulingsamples.schedule.type;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class WeeklySchedule implements Schedule {
    private final Set<DayOfWeek> daysOfWeek;

    public WeeklySchedule(Set<DayOfWeek> daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException("Days of week cannot be null or empty");
        }
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public boolean isScheduledFor(@Nonnull LocalDate date) {
        return this.daysOfWeek.contains(date.getDayOfWeek());
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.WEEKLY;
    }
}
