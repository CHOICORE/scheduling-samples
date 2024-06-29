package me.choicore.demo.schedulingsamples.schedule.type;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;
import me.choicore.demo.schedulingsamples.schedule.unit.WeekOfMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class ComplexSchedule implements Schedule {
    /**
     * 주차 (1 ~ 5, 마지막 주차: -1)
     */
    private final Set<WeekOfMonth> weeksOfMonth;

    /**
     * 요일
     */
    private final Set<DayOfWeek> daysOfWeek;

    public ComplexSchedule(Set<WeekOfMonth> weeksOfMonth, Set<DayOfWeek> daysOfWeek) {
        validate(weeksOfMonth, daysOfWeek);
        this.weeksOfMonth = weeksOfMonth;
        this.daysOfWeek = daysOfWeek;
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
                && daysOfWeek.contains(date.getDayOfWeek());
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.SPECIFIC;
    }
}
