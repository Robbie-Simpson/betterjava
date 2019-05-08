package com.revolut.java;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.revolut.java.BetterOptional.*;
import static org.assertj.core.api.Assertions.*;

class BetterOptionalSpec {

    private final static String PRESENT = "some-value";
    private final static String ABSENT = null;

    @Test
    void optional_with_present_value() {
        assertThat(optional(PRESENT))
            .isEqualTo(Optional.of(PRESENT));
    }

    @Test
    void optional_with_absent_value() {
        assertThat(optional(ABSENT))
            .isEqualTo(Optional.empty());
    }

    @Test
    void some_with_present_value() {
        assertThat(some(PRESENT))
            .isEqualTo(Optional.of(PRESENT));
    }

    @Test
    void some_with_absent_value() {
        assertThatNullPointerException()
            .isThrownBy(() -> some(ABSENT))
            .withMessage("Some value cannot be null");
    }

    @Test
    void absent_value() {
        assertThat(absent())
            .isEqualTo(Optional.empty());
    }
}