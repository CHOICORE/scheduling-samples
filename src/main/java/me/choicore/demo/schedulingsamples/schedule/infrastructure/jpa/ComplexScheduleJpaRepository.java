package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.ComplexScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplexScheduleJpaRepository extends JpaRepository<ComplexScheduleEntity, Long> {
    List<ComplexScheduleEntity> findByScheduledMonthAndScheduledWeekOfMonth(int scheduledMonth, int scheduledWeekOfMonth);
}
