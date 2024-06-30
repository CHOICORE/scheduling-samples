package me.choicore.demo.schedulingsamples.schedule.repository.jpa;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.ComplexScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexScheduleJpaRepository extends JpaRepository<ComplexScheduleEntity, Long> {

}
