package me.choicore.demo.schedulingsamples.schedule.repository.jpa;

import me.choicore.demo.schedulingsamples.schedule.repository.jpa.entity.OnceScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OnceScheduleJpaRepositoryTests {
    @Autowired
    private OnceScheduleJpaRepository repository;

    @Test
    @DisplayName("스케줄 저장을 성공한다.")
    void t1() {
        // given
        var schedule = new OnceSchedule(LocalDate.now());
        long fakeScheduleId = 1L;

        // when
        OnceScheduleEntity saved = repository.save(OnceScheduleEntity.create(fakeScheduleId, schedule));

        // then
        assertThat(saved.getId()).isNotNull().isGreaterThan(0);
    }
}