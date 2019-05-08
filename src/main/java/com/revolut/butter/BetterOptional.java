package com.revolut.butter;

import java.util.Objects;
import java.util.Optional;

public final class BetterOptional {

    private BetterOptional() {
        throw new AssertionError();
    }

    @NotNull
    public static <T> Optional<T> optional(T value) {
        return Optional.ofNullable(value);
    }

    @NotNull
    public static <T> Optional<T> some(@NotNull("value") T value) {
        return Optional.of(
            Objects.requireNonNull(value, "Some value cannot be null")
        );
    }

    @NotNull
    public static <T> Optional<T> absent() {
        return Optional.empty();
    }
}
