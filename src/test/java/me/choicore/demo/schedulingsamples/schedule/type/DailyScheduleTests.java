package me.choicore.demo.schedulingsamples.schedule.type;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DailyScheduleTests {
    @Test
    @DisplayName("Daily Schedule 은 항상 스케줄되어 있다.")
    void t1() {
        // given
        var schedule = new DailySchedule();
        // when
        boolean scheduled = schedule.isScheduledFor(LocalDate.now());

        // then
        Assertions.assertThat(scheduled).isTrue();
    }

    @Test
    @DisplayName("Daily Schedule argument 를 무시하고 항상 스케줄되어 있다고 판단한다.")
    void t2() {
        // given
        var schedule = new DailySchedule();
        // when
        boolean scheduled = schedule.isScheduledFor(null);

        // then
        Assertions.assertThat(scheduled).isTrue();
    }
}