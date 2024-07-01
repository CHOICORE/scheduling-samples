package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import jakarta.annotation.Nonnull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.WeeklyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyScheduleJpaRepository extends JpaRepository<WeeklyScheduleEntity, Long> {
    List<WeeklyScheduleEntity> findAllByPeriodicity(@Nonnull final Periodicity periodicity);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledMonday(@Nonnull final Periodicity periodicity, boolean scheduledMonday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledTuesday(@Nonnull final Periodicity periodicity, boolean scheduledTuesday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledWednesday(@Nonnull final Periodicity periodicity, boolean scheduledWednesday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledThursday(@Nonnull final Periodicity periodicity, boolean scheduledThursday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledFriday(@Nonnull final Periodicity periodicity, boolean scheduledFriday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledSaturday(@Nonnull final Periodicity periodicity, boolean scheduledSaturday);

    List<WeeklyScheduleEntity> findByPeriodicityAndScheduledSunday(@Nonnull final Periodicity periodicity, boolean scheduledSunday);
}
