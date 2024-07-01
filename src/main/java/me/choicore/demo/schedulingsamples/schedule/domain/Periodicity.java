package me.choicore.demo.schedulingsamples.schedule.domain;

public enum Periodicity {
    /**
     * 한 번만 발생하는 경우
     */
    ONCE,

    /**
     * 매일 발생하는 경우
     */
    DAILY,

    /**
     * 매주 발생하는 경우
     */
    WEEKLY,

    /**
     * 매달 발생하는 경우
     */
    MONTHLY,

    /**
     * 복잡한 조건 (예: 매달 첫째 주 월요일)
     */
    SPECIFIC,
}