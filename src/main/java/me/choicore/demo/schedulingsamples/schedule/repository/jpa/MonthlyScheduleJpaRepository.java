package me.choicore.demo.schedulingsamples.schedule.repository.jpa;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.MonthlyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyScheduleJpaRepository extends JpaRepository<MonthlyScheduleEntity, Long> {

}
