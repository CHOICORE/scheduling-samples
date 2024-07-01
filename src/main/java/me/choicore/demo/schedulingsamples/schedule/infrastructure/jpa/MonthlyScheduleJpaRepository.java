package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.MonthlyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyScheduleJpaRepository extends JpaRepository<MonthlyScheduleEntity, Long> {

}
