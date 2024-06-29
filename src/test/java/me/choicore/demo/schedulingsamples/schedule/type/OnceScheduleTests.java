package me.choicore.demo.schedulingsamples.schedule.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OnceScheduleTests {
    @Test
    @DisplayName("스케줄된 날짜에 대해 스케줄되어 있는지 확인")
    void t1() {
        // given
        LocalDate date = LocalDate.of(2024, 2, 29);
        var sut = new OnceSchedule(date);

        // when
        boolean scheduled = sut.isScheduledFor(date);

        // then
        assertThat(scheduled).isTrue();
    }

    @Test
    @DisplayName("스케줄되지 않은 날짜에 대해 스케줄되어 있는지 확인")
    void t2() {
        // given
        LocalDate date = LocalDate.of(2024, 2, 29);
        var sut = new OnceSchedule(date);

        // when
        boolean scheduled = sut.isScheduledFor(date.plusDays(1));

        // then
        assertThat(scheduled).isFalse();
    }
}