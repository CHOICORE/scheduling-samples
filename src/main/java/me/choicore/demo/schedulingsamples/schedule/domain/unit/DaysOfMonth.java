package me.choicore.demo.schedulingsamples.schedule.domain.unit;

import java.time.Month;
import java.util.Set;

public record DaysOfMonth(
        Month month,
        Set<Day> days
) {
}
