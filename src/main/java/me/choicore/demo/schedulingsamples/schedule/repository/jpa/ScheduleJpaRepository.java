package me.choicore.demo.schedulingsamples.schedule.repository.jpa;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
}
