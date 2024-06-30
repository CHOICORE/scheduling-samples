package me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.type.WeeklySchedule;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "weekly_schedule")
public class WeeklyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long scheduleId;

    @Enumerated(EnumType.STRING)
    private Periodicity periodicity = Periodicity.WEEKLY;
    private boolean scheduledMonday;
    private boolean scheduledTuesday;
    private boolean scheduledWednesday;
    private boolean scheduledThursday;
    private boolean scheduledFriday;
    private boolean scheduledSaturday;
    private boolean scheduledSunday;

    public WeeklyScheduleEntity(
            @NonNull final Long scheduleId,
            final boolean scheduledMonday,
            final boolean scheduledTuesday,
            final boolean scheduledWednesday,
            final boolean scheduledThursday,
            final boolean scheduledFriday,
            final boolean scheduledSaturday,
            final boolean scheduledSunday
    ) {
        this.scheduleId = scheduleId;
        this.scheduledMonday = scheduledMonday;
        this.scheduledTuesday = scheduledTuesday;
        this.scheduledWednesday = scheduledWednesday;
        this.scheduledThursday = scheduledThursday;
        this.scheduledFriday = scheduledFriday;
        this.scheduledSaturday = scheduledSaturday;
        this.scheduledSunday = scheduledSunday;
    }

    public static WeeklyScheduleEntity create(
            @Nonnull final Long scheduleId,
            @Nonnull final WeeklySchedule schedule
    ) {
        Set<DayOfWeek> dayOfWeeks = schedule.daysOfWeek();
        return new WeeklyScheduleEntity(
                scheduleId,
                dayOfWeeks.contains(DayOfWeek.MONDAY),
                dayOfWeeks.contains(DayOfWeek.TUESDAY),
                dayOfWeeks.contains(DayOfWeek.WEDNESDAY),
                dayOfWeeks.contains(DayOfWeek.THURSDAY),
                dayOfWeeks.contains(DayOfWeek.FRIDAY),
                dayOfWeeks.contains(DayOfWeek.SATURDAY),
                dayOfWeeks.contains(DayOfWeek.SUNDAY)
        );
    }

    public static WeeklyScheduleEntity specificOf(
            @Nonnull final Long scheduleId,
            @Nonnull final WeeklySchedule schedule
    ) {
        WeeklyScheduleEntity weeklyScheduleEntity = create(scheduleId, schedule);
        weeklyScheduleEntity.periodicity = Periodicity.SPECIFIC;
        return weeklyScheduleEntity;
    }

    public WeeklySchedule toWeeklySchedule() {
        EnumSet<DayOfWeek> dayOfWeeks = EnumSet.noneOf(DayOfWeek.class);

        if (scheduledMonday) {
            dayOfWeeks.add(DayOfWeek.MONDAY);
        }
        if (scheduledTuesday) {
            dayOfWeeks.add(DayOfWeek.TUESDAY);
        }
        if (scheduledWednesday) {
            dayOfWeeks.add(DayOfWeek.WEDNESDAY);
        }
        if (scheduledThursday) {
            dayOfWeeks.add(DayOfWeek.THURSDAY);
        }
        if (scheduledFriday) {
            dayOfWeeks.add(DayOfWeek.FRIDAY);
        }
        if (scheduledSaturday) {
            dayOfWeeks.add(DayOfWeek.SATURDAY);
        }
        if (scheduledSunday) {
            dayOfWeeks.add(DayOfWeek.SUNDAY);
        }

        return new WeeklySchedule(
                dayOfWeeks
        );
    }
}
