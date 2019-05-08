package com.revolut.java;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.TimeZone;
import java.util.function.IntSupplier;

import static com.revolut.java.TimeProvider.*;
import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TimeProviderJDK8Test {

    @BeforeEach
    void beforeMethod() {
        assumeTrue(SystemUtils.IS_JAVA_1_8);
    }

    @Test
    void verify_default_timezone_is_set_to_Utc() {
        clock();
        assertEquals("UTC", TimeZone.getDefault().getID());
    }

    @Test
    void should_return_a_different_clock_reference_with_test_mode_disabled() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        assertEquals(Clock.systemUTC(), clock());
    }

    @Test
    void should_calculate_current_instant_with_milliseconds_precision_with_test_mode_disabled_in_jdk_1_8() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> instant().getNano());
    }

    @Test
    void should_calculate_current_local_date_time_with_milliseconds_precision_with_test_mode_disabled_in_jdk_1_8() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> dateTime().getNano());
    }

    @Test
    void should_calculate_current_offset_date_time_with_milliseconds_precision_with_test_mode_disabled_in_jdk_1_8() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> offsetDateTime().getNano());
    }

    @Test
    void should_calculate_current_zone_date_time_with_milliseconds_precision_with_test_mode_disabled_in_jdk_1_8() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> zonedDateTime().getNano());
    }

    @Test
    void should_calculate_current_local_time_with_milliseconds_precision_with_test_mode_disabled_in_jdk_1_8() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->time().getNano());
    }

    @Test
    void should_return_a_different_clock_reference_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        assertNotSame(Clock.systemUTC(), clock());
    }

    @Test
    void should_calculate_current_instant_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->instant().getNano());
    }

    @Test
    void should_calculate_current_local_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->dateTime().getNano());
    }

    @Test
    void should_calculate_current_offset_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->offsetDateTime().getNano());
    }

    @Test
    void should_calculate_current_zoned_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->zonedDateTime().getNano());
    }

    @Test
    void should_calculate_current_local_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->time().getNano());
    }


    private void verifyMillisecondsResolution(final IntSupplier nanosecondsSupplier) {
        int successful = 0;
        for (int i = 0; i < 3; i++) {
            if (hasMillisecondPrecision(nanosecondsSupplier.getAsInt())) successful ++;
        }
        assertEquals(3, successful);
    }

    private boolean hasMillisecondPrecision(final int nanos) {
        return String.valueOf(nanos).replace("0", "").length() <= 3;
    }
}
