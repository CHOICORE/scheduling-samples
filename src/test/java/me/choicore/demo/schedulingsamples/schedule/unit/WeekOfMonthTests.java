package me.choicore.demo.schedulingsamples.schedule.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

class WeekOfMonthTests {
    @Test
    @DisplayName("마지막 주차에 해당하는 날짜가 포함되는지 확인")
    void t1() {
        WeekOfMonth sut = WeekOfMonth.of(Month.JULY, Week.LAST);
        boolean within = sut.isWithin(LocalDate.of(2024, 7, 26));
        Assertions.assertThat(within).isTrue();
    }

    @Test
    @DisplayName("네 번째 주차에 해당하는 날짜가 포함되는지 확인")
    void t2() {
        WeekOfMonth sut = WeekOfMonth.of(Month.JULY, Week.FOURTH);
        boolean within = sut.isWithin(LocalDate.of(2024, 7, 26));
        Assertions.assertThat(within).isTrue();
    }

    @Test
    @DisplayName("마지막 주차에 해당하는 날짜가 포함되지 않는지 확인")
    void t3() {
        // given
        LocalDate date = LocalDate.of(2024, 7, 29);
        WeekOfMonth sut = WeekOfMonth.of(Month.JULY, Week.LAST);

        // when
        boolean within = sut.isWithin(date);

        // then
        Assertions.assertThat(within).isFalse();
        WeekOfMonth weekOfMonth = WeekOfMonth.of(date);
        Assertions.assertThat(weekOfMonth.month()).isEqualTo(Month.AUGUST);
        Assertions.assertThat(weekOfMonth.week()).isEqualTo(Week.FIRST);
    }

    @Test
    @DisplayName("이전 달의 마지막 날짜가 목요일 이전인 경우 날짜가 다음 달의 첫 주차에 포함되는지 확인")
    void t4() {
        LocalDate date = LocalDate.of(2024, 7, 29);
        WeekOfMonth sut = WeekOfMonth.of(date);
        Assertions.assertThat(sut.month()).isEqualTo(Month.AUGUST);
        Assertions.assertThat(sut.week()).isEqualTo(Week.FIRST);
    }

    @Test
    @DisplayName("해당 날짜가 해당 월의 마지막 주차에 포함되는지 확인")
    void t5() {
        LocalDate date = LocalDate.of(2024, 6, 24);
        WeekOfMonth lastWeek = WeekOfMonth.of(Month.JUNE, Week.LAST);
        Assertions.assertThat(lastWeek.isWithin(date)).isTrue();

        WeekOfMonth weekOfMonth = WeekOfMonth.of(date);
        Assertions.assertThat(weekOfMonth.isWithin(date)).isTrue();
    }

    @Test
    @DisplayName("해당 날짜가 해당 월의 4주차, 5주차 마지막 주차로 예상되는 경우 마지막 주차에 포함되는지 확인")
    void t6() {
        LocalDate date = LocalDate.of(2024, 4, 28);
        WeekOfMonth sut = WeekOfMonth.of(date);
        Assertions.assertThat(sut.month()).isEqualTo(Month.APRIL);
        Assertions.assertThat(sut.week()).isEqualTo(Week.FOURTH);
        Assertions.assertThat(sut.isLastWeek(date)).isTrue();
    }
}