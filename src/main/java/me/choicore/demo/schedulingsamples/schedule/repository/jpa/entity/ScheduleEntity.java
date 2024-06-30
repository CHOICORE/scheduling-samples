package me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.Schedule;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public static ScheduleEntity create(Schedule schedule) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.periodicity = schedule.getPeriodicity();
        return entity;
    }
}
