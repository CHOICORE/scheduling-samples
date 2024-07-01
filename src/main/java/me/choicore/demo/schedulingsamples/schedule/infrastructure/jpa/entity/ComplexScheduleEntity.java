package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.domain.type.ComplexSchedule;
import me.choicore.demo.schedulingsamples.schedule.domain.unit.WeekOfMonth;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "complex_schedule")
public class ComplexScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;

    private Long weeklyScheduleId;

    private int scheduledMonth;

    private int scheduledWeekOfMonth;

    public static List<ComplexScheduleEntity> create(long scheduleId, Long weeklyScheduleId, ComplexSchedule schedule) {
        List<ComplexScheduleEntity> entities = new ArrayList<>();
        for (WeekOfMonth weekOfMonth : schedule.weeksOfMonth()) {
            ComplexScheduleEntity entity = new ComplexScheduleEntity();
            entity.scheduleId = scheduleId;
            entity.weeklyScheduleId = weeklyScheduleId;
            entity.scheduledMonth = weekOfMonth.month().getValue();
            entity.scheduledWeekOfMonth = weekOfMonth.week().numberOfWeek();
            entities.add(entity);
        }
        return entities;
    }
}
