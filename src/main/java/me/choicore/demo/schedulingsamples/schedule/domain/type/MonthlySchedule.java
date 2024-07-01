package me.choicore.demo.schedulingsamples.schedule.domain.type;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.domain.Schedule;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.Day;

import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class MonthlySchedule implements Schedule {
    /**
     * 월 (1 ~ 12)
     */
    private final Set<Month> months;
    /**
     * 일 (1 ~ 31)
     */
    private final Set<Day> days;

    public MonthlySchedule(Set<Month> months, Set<Day> days) {
        validate(months, days);
        this.months = months;
        this.days = days;
    }

    public MonthlySchedule(Set<Day> days) {
        this(EnumSet.allOf(Month.class), days);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlySchedule that = (MonthlySchedule) o;
        return Objects.equals(getMonths(), that.getMonths()) && Objects.equals(getDays(), that.getDays());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMonths(), getDays());
    }

    private void validate(Set<Month> months, Set<Day> days) {
        if (months == null || months.isEmpty()) {
            throw new IllegalArgumentException("Months cannot be null or empty");
        }
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days cannot be null or empty");
        }
    }

    @Override
    public boolean isScheduledFor(@Nonnull LocalDate date) {
        return months.contains(date.getMonth()) && days.contains(Day.of(date.getDayOfMonth()));
    }

    @Override
    public Periodicity getPeriodicity() {
        return Periodicity.MONTHLY;
    }
}
