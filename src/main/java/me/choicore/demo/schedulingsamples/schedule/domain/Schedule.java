package me.choicore.demo.schedulingsamples.schedule.domain;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public interface Schedule {
    boolean isScheduledFor(@Nonnull LocalDate date);

    Periodicity getPeriodicity();
}
