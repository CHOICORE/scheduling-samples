package me.choicore.demo.schedulingsamples.schedule.repository.jpa;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ComplexScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplexScheduleJpaRepository extends JpaRepository<ComplexScheduleEntity, Long> {
    List<ComplexScheduleEntity> findByScheduledMonthAndScheduledWeekOfMonth(int scheduledMonth, int scheduledWeekOfMonth);
}
