package me.choicore.demo.schedulingsamples.schedule.unit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

public record WeekOfMonth(
        Month month,
        Week week
) {

    public static final int MINIMAL_DAYS_IN_FIRST_WEEK = DayOfWeek.THURSDAY.getValue();
    public static final DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY;
    public static final WeekFields WEEK_FIELDS = WeekFields.of(FIRST_DAY_OF_WEEK, MINIMAL_DAYS_IN_FIRST_WEEK);

    public static WeekOfMonth of(Month month, Week week) {
        return new WeekOfMonth(month, week);
    }

    public static WeekOfMonth of(LocalDate date) {
        // 해당 월의 첫번째 날 구하기
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        // 해당 월의 첫번째 날이 목요일 이후라면 이전달의 마지막 주차를 구해야 함
        if (firstDayOfMonth.getDayOfWeek().getValue() > MINIMAL_DAYS_IN_FIRST_WEEK) {
            // 해당 월의 첫번째 날이 목요일 이후인 경우 해당 주의 시작 날을 구함 (전 달 마지막 주의 월요일을 구함)
            LocalDate weekStart = firstDayOfMonth.with(FIRST_DAY_OF_WEEK);

            // 해당 일의 날짜가 전 달 마지막 주에 포함 되는지 확인
            if (date.isBefore(weekStart.plusWeeks(1))) {
                return new WeekOfMonth(weekStart.getMonth(), Week.valueOf(weekStart.get(WEEK_FIELDS.weekOfMonth())));
            }
        }

        // 해당 월의 마지막 날 구하기
        LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        // 마지막 날이 목요일 이전인지 확인
        if (lastDayOfMonth.getDayOfWeek().getValue() < MINIMAL_DAYS_IN_FIRST_WEEK) {
            // 마지막 주의 시작 날을 구함
            LocalDate lastWeekStart = lastDayOfMonth.with(FIRST_DAY_OF_WEEK);

            // 해당 일의 날짜가 다음 달 첫 주에 포함 되는지 확인
            if (date.isAfter(lastWeekStart.minusDays(1))) {
                LocalDate nextMonthDate = date.plusWeeks(1);
                return new WeekOfMonth(nextMonthDate.getMonth(), Week.FIRST);
            }
        }

        int weekOfMonth = date.get(WEEK_FIELDS.weekOfMonth());
        return new WeekOfMonth(date.getMonth(), Week.valueOf(weekOfMonth));
    }


    public boolean isWithin(LocalDate date) {
        if (Week.LAST == this.week) {
            return isLastWeek(date);
        }
        if (Week.FOURTH == this.week || Week.FIFTH == this.week) {
            return isLastWeek(date) || this.equals(WeekOfMonth.of(date));
        }

        return this.equals(WeekOfMonth.of(date));
    }

    public boolean isLastWeek(LocalDate date) {
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfLastWeek = lastDayOfMonth.with(TemporalAdjusters.previousOrSame(FIRST_DAY_OF_WEEK));
        if (lastDayOfMonth.getDayOfWeek().getValue() < MINIMAL_DAYS_IN_FIRST_WEEK) {
            LocalDate adjustedPreviousLastDayOfLastWeek = firstDayOfLastWeek.minusDays(8);
            return date.isAfter(adjustedPreviousLastDayOfLastWeek) && date.isBefore(firstDayOfLastWeek);
        } else {
            return date.isAfter(lastDayOfMonth.minusDays(7)) && date.isBefore(lastDayOfMonth.plusDays(1));
        }
    }
}
