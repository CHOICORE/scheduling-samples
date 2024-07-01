package me.choicore.demo.schedulingsamples.schedule.domain.type;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.WeekOfMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * @param weeksOfMonth 주차 (1 ~ 5, 마지막 주차: -1)
 * @param weeklySchedule   요일
 */
public record ComplexSchedule(
        Set<WeekOfMonth> weeksOfMonth,
        WeeklySchedule weeklySchedule
) implements Schedule {
    public ComplexSchedule(Set<WeekOfMonth> weeksOfMonth, Set<DayOfWeek> daysOfWeek) {
        this(weeksOfMonth, new WeeklySchedule(daysOfWeek));
    }

    public ComplexSchedule {
        validate(weeksOfMonth, weeklySchedule.daysOfWeek());
    }

    private void validate(Set<WeekOfMonth> weeksOfMonth, Set<DayOfWeek> daysOfWeek) {
        if (weeksOfMonth == null || weeksOfMonth.isEmpty()) {
            throw new IllegalArgumentException("Weeks of month cannot be null or empty");
        }
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException("Days of week cannot be null or empty");
        }
    }

    @Override
    public boolean isScheduledFor(@Nonnull LocalDate date) {
        return weeksOfMonth.stream().anyMatch(f -> f.isWithin(date))
                && weeklySchedule.daysOfWeek().contains(date.getDayOfWeek());
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.SPECIFIC;
    }

}
