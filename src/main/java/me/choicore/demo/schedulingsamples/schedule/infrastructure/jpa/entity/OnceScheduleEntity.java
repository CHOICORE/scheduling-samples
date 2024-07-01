package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "once_schedule")
public class OnceScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;

    private LocalDate date;

    public OnceScheduleEntity(Long scheduleId, LocalDate date) {
        this.scheduleId = scheduleId;
        this.date = date;
    }

    public static OnceScheduleEntity create(Long scheduleId, OnceSchedule schedule) {
        return new OnceScheduleEntity(scheduleId, schedule.date());
    }

    public OnceSchedule toOnceSchedule() {
        return new OnceSchedule(date);
    }
}
