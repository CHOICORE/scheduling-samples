package me.choicore.demo.schedulingsamples.schedule.unit;

import java.time.YearMonth;
import java.util.LinkedHashSet;
import java.util.Set;

public record Day(
        int value
) {
    public static final Day FIRST_DAY = new Day(1);

    public Day {
        if (value < 1 || value > 31) {
            throw new IllegalArgumentException("Day value must be between 1 and 31");
        }
    }

    public static Set<Day> daysOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        Set<Day> days = new LinkedHashSet<>();
        for (int i = 0; i < yearMonth.lengthOfMonth(); i++) {
            days.add(new Day(i + 1));
        }

        return days;
    }

    /**
     * @param value day value
     * @return Day
     * @throws IllegalArgumentException if the value is less than 1 or greater than 31
     */
    public static Day of(int value) {
        return new Day(value);
    }
}
