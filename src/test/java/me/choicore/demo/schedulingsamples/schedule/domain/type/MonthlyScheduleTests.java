package me.choicore.demo.schedulingsamples.schedule.domain.type;

import me.choicore.demo.schedulingsamples.schedule.domain.unit.Day;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MonthlyScheduleTests {
    static Stream<LocalDate> getFirstDayOfMonth() {
        return Stream.of(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("getFirstDayOfMonth")
    @DisplayName("매월 1일이 스케줄되어 있다.")
    void t1(LocalDate date) {
        // given
        var schedule = new MonthlySchedule(Set.of(Day.FIRST_DAY));

        // when
        boolean scheduled = schedule.isScheduledFor(date);

        // then
        assertThat(scheduled).isTrue();
    }

    @Test
    @DisplayName("특정 월 전체 날짜가 스케줄되어 있다.")
    void t2() {
        // given
        Set<Day> februaryInDays = Day.daysOfMonth(2024, 2);
        var schedule = new MonthlySchedule(Set.of(Month.FEBRUARY), februaryInDays);

        // when
        for (Day februaryInDay : februaryInDays) {
            boolean scheduled = schedule.isScheduledFor(LocalDate.of(2024, 2, februaryInDay.value()));

            // then
            assertThat(scheduled).isTrue();
        }
    }

    @Test
    @DisplayName("월과 일의 조합이 스케줄되지 않은 경우를 테스트")
    void testDateNotScheduled() {
        // given
        var schedule = new MonthlySchedule(Set.of(Month.JANUARY), Set.of(Day.of(1), Day.of(15)));

        // when
        boolean scheduled = schedule.isScheduledFor(LocalDate.of(2024, 1, 10));

        // then
        assertThat(scheduled).isFalse();
    }
}