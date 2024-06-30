package me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter;

import me.choicore.demo.schedulingsamples.schedule.ScheduleWrapper;
import me.choicore.demo.schedulingsamples.schedule.repository.jpa.adapter.support.TestOnceScheduleRepository;
import me.choicore.demo.schedulingsamples.schedule.type.OnceSchedule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Consumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OnceScheduleRepositoryTests {
    @Autowired
    private TestOnceScheduleRepository onceScheduleRepository;

    @AfterEach
    void setUp() {
        onceScheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("단 한 번만 실행되는 스케줄을 저장하고 조회한다.")
    void t1() {
        // given
        LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        var schedule = new OnceSchedule(LocalDate.now(fixedClock));
        long dummyScheduleId = 1L;

        // when
        onceScheduleRepository.save(new ScheduleWrapper<>(dummyScheduleId, schedule));

        // then
        onceScheduleRepository.isScheduledFor(fixedDate).forEach(assertionIsScheduledFor(fixedDate, true));
    }

    @Test
    @DisplayName("예약 된 날짜가 아닌 경우 조회 결과가 없다.")
    void t2() {
        // given
        LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        var schedule = new OnceSchedule(LocalDate.now(fixedClock));
        long dummyScheduleId = 1L;

        // when
        onceScheduleRepository.save(new ScheduleWrapper<>(dummyScheduleId, schedule));

        // then
        Assertions.assertThat(onceScheduleRepository.isScheduledFor(LocalDate.now())).isEmpty();
    }

    private Consumer<ScheduleWrapper<OnceSchedule>> assertionIsScheduledFor(LocalDate fixedDate, boolean expected) {
        return it -> Assertions.assertThat(it.schedule().isScheduledFor(fixedDate)).isEqualTo(expected);
    }
}