package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.domain.Periodicity;
import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByPeriodicity(Periodicity periodicity);
}
