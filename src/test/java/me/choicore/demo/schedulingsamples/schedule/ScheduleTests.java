package me.choicore.demo.schedulingsamples.schedule;

import me.choicore.demo.schedulingsamples.schedule.type.*;
import me.choicore.demo.schedulingsamples.schedule.unit.Day;
import me.choicore.demo.schedulingsamples.schedule.unit.Week;
import me.choicore.demo.schedulingsamples.schedule.unit.WeekOfMonth;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class ScheduleTests {

    @Nested
    class OnceScheduleTests {

        void t1() {
            // given
            LocalDate date = LocalDate.of(2024, 2, 29);

            // when
            var schedule = new OnceSchedule(date);
            // then
            boolean scheduled = schedule.isScheduledFor(date);
            assertThat(scheduled).isTrue();
            assertThat(schedule.isScheduledFor(date.plusDays(1))).isFalse();
            assertThat(schedule.isScheduledFor(date.minusDays(1))).isFalse();
        }
    }

    @Nested
    class DailyScheduleTests {
        static Stream<LocalDate> provideRandomDates() {
            Random random = new Random();
            return Stream.generate(() -> {
                int randomYear = 2000 + random.nextInt(30);  // Year between 2000 and 2029
                int randomDayOfYear = 1 + random.nextInt(365); // Day of the year between 1 and 365
                return LocalDate.ofYearDay(randomYear, randomDayOfYear);
            }).limit(1);
        }

        @ParameterizedTest
        @MethodSource("provideRandomDates")
        void t1(LocalDate date) {
            // given
            var schedule = new DailySchedule();

            // when
            boolean scheduled = schedule.isScheduledFor(date);

            // then
            assertThat(scheduled).isTrue();
        }
    }

    @Nested
    class WeeklyScheduleTests {
        @Test
        void t1() {
            // given
            Set<DayOfWeek> daysOfWeek = EnumSet.noneOf(DayOfWeek.class);
            daysOfWeek.add(DayOfWeek.MONDAY);
            LocalDate date = LocalDate.of(2024, 2, 29);
            var schedule = new WeeklySchedule(daysOfWeek);

            // when
            boolean scheduled = schedule.isScheduledFor(date);

            // then
            Assertions.assertThat(scheduled).isFalse();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            Assertions.assertThat(daysOfWeek).isNotIn(dayOfWeek);
        }
    }

    @Nested
    class MonthlyScheduleTests {
        @Test
        void t1() {
            // given
            Set<Month> months = Set.of(Month.JULY, Month.AUGUST);
            Set<Day> days = Set.of(Day.of(1), Day.of(15), Day.of(31));

            var schedule = new MonthlySchedule(months, days);

            // then
            LocalDate dayInFebruary = LocalDate.of(2024, 2, 1);
            Assertions.assertThat(schedule.isScheduledFor(dayInFebruary)).isFalse();
            LocalDate dayInJuly = LocalDate.of(2024, 7, 1);
            Assertions.assertThat(schedule.isScheduledFor(dayInJuly)).isTrue();
            LocalDate dayInAugust = LocalDate.of(2024, 8, 15);
            Assertions.assertThat(schedule.isScheduledFor(dayInAugust)).isTrue();
        }
    }

    @Nested
    class ComplexScheduleTests {
        @Test
        void t1() {
            // 7월 마지막 주
            Set<WeekOfMonth> weeksOfMonth = Set.of(new WeekOfMonth(Month.JULY, Week.LAST));

            // 월, 금
            Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

            var schedule = new ComplexSchedule(weeksOfMonth, daysOfWeek);

            Assertions.assertThat(schedule.isScheduledFor(LocalDate.of(2024, 7, 22))).isTrue();
            Assertions.assertThat(schedule.isScheduledFor(LocalDate.of(2024, 7, 26))).isTrue();
            Assertions.assertThat(schedule.isScheduledFor(LocalDate.of(2024, 7, 29))).isFalse();
        }

        @Test
        @DisplayName("주차 계산 법에 의해 7월 29일은 8월 1주차로 판단됨.")
        void t2() {
            // 8월 첫번째 주
            Set<WeekOfMonth> weeksOfMonth = Set.of(new WeekOfMonth(Month.AUGUST, Week.FIRST));

            // 월, 금
            Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

            var schedule = new ComplexSchedule(weeksOfMonth, daysOfWeek);

            Assertions.assertThat(schedule.isScheduledFor(LocalDate.of(2024, 7, 29))).isTrue();
            Assertions.assertThat(schedule.isScheduledFor(LocalDate.of(2024, 8, 2))).isTrue();
        }
    }
}

