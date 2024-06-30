package me.choicore.demo.schedulingsamples.schedule;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;


public record ScheduleWrapper<T extends Schedule>(Long id, T schedule) implements Schedule {

    @Override
    public boolean isScheduledFor(@Nonnull LocalDate date) {
        return this.schedule.isScheduledFor(date);
    }


    @Override
    public Periodicity getPeriodicity() {
        return this.schedule.getPeriodicity();
    }
}
