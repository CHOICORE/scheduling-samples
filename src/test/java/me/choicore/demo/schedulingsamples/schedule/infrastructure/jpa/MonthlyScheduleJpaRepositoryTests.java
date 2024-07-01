package me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa;

import me.choicore.demo.schedulingsamples.schedule.infrastructure.jpa.entity.MonthlyScheduleEntity;
import me.choicore.demo.schedulingsamples.schedule.type.MonthlySchedule;
import me.choicore.demo.schedulingsamples.schedule.unit.Day;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Month;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MonthlyScheduleJpaRepositoryTests {
    @Autowired
    private MonthlyScheduleJpaRepository repository;

    @Test
    @DisplayName("스케줄 저장을 성공한다.")
    void t1() {
        // given
        var schedule = new MonthlySchedule(Set.of(Day.FIRST_DAY));
        List<MonthlyScheduleEntity> entities = MonthlyScheduleEntity.create(1L, schedule);

        // when
        List<MonthlyScheduleEntity> saved = repository.saveAll(entities);

        // then
        assertThat(saved).hasSize(schedule.getMonths().size() * schedule.getDays().size());
        assertThat(saved).allMatch(entity -> entity.getId() != null && entity.getId() > 0L);
    }

    @Test
    @DisplayName("등록한 스케줄 엔티티를 도메인 모델로 변환한다.")
    void t2() {
        var schedule = new MonthlySchedule(Set.of(Day.FIRST_DAY));
        List<MonthlyScheduleEntity> entities = MonthlyScheduleEntity.create(1L, schedule);

        Set<Month> months = entities.stream().map(it -> {
            int scheduledMonth = it.getScheduledMonth();
            return Month.of(scheduledMonth);
        }).sorted(Comparator.comparing(Month::getValue)).collect(Collectors.toCollection(LinkedHashSet::new));

        Set<Day> days = entities.stream().map(it -> {
            int scheduledDay = it.getScheduledDay();
            return Day.of(scheduledDay);
        }).sorted(Comparator.comparingInt(Day::value)).collect(Collectors.toCollection(LinkedHashSet::new));

        MonthlySchedule convert = new MonthlySchedule(months, days);

        assertThat(convert).isEqualTo(schedule);
    }
}