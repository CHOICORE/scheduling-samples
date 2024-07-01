package me.choicore.demo.schedulingsamples.schedule.domain.type;


import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * @param daysOfWeek 요일
 */
public record WeeklySchedule(Set<DayOfWeek> daysOfWeek) implements Schedule {
    public WeeklySchedule {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException("Days of week cannot be null or empty");
        }
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
