package me.choicore.demo.schedulingsamples.schedule.unit;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Week {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    LAST(-1);

    private static final Map<Integer, Week> registry = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(Week::numberOfWeek, Function.identity()));
    private final int numberOfWeek;

    Week(int numberOfWeek) {
        this.numberOfWeek = numberOfWeek;
    }

    public static Week valueOf(int value) {
        return Optional.ofNullable(registry.get(value))
                .orElseThrow(() -> new IllegalArgumentException("Invalid week value: " + value));
    }

    public int numberOfWeek() {
        return numberOfWeek;
    }
}
