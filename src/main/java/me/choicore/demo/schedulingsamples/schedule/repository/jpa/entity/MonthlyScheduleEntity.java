package me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.type.MonthlySchedule;
import me.choicore.demo.schedulingsamples.schedule.unit.Day;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "monthly_schedule")
public class MonthlyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;

    private int scheduledMonth;

    private int scheduledDay;

    public MonthlyScheduleEntity(
            @Nonnull final Long scheduleId,
            final int scheduledMonth,
            final int scheduledDay
    ) {
        this.scheduleId = scheduleId;
        this.scheduledMonth = scheduledMonth;
        this.scheduledDay = scheduledDay;
    }

    public static List<MonthlyScheduleEntity> create(
            @Nonnull final Long scheduleId,
            @Nonnull final MonthlySchedule schedule
    ) {
        Set<Month> months = schedule.getMonths();
        Set<Day> days = schedule.getDays();

        List<MonthlyScheduleEntity> entities = new ArrayList<>();

        for (Month month : months) {
            for (Day day : days) {
                MonthlyScheduleEntity entity = new MonthlyScheduleEntity(scheduleId, month.getValue(), day.value());
                entities.add(entity);

            }
        }
        return entities;
    }
}
