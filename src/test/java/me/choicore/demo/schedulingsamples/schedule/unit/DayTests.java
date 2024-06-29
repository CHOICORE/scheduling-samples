package me.choicore.demo.schedulingsamples.schedule.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DayTests {
    @Test
    void t1() {
        Set<Day> days = Day.daysOfMonth(2024, 2);
        assertThat(days).hasSize(29);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 32})
    void t2(int dayValue) {
        assertThatThrownBy(() -> Day.of(dayValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Day value must be between 1 and 31");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void t3(int dayValue) {
        Day day = Day.of(dayValue);
        assertThat(day.value()).isEqualTo(dayValue);
    }
}