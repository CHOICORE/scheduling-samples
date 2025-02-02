package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.OnceScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OnceScheduleJpaRepository extends JpaRepository<OnceScheduleEntity, Long> {

    List<OnceScheduleEntity> findByDate(LocalDate date);
}
