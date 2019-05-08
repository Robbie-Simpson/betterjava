package com.revolut.butter;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.*;

import java.time.Clock;
import java.util.TimeZone;
import java.util.function.IntSupplier;

import static com.revolut.butter.TimeProvider.*;
import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TimeProviderJDK11Test {

    @BeforeEach
    void beforeMethod() {
        assumeTrue(SystemUtils.IS_JAVA_11);
    }

    @Test
    void verify_default_timezone_is_set_to_Utc() {
        clock();
        assertEquals("UTC", TimeZone.getDefault().getID());
    }

    @Test
    void should_return_the_system_clock_reference_with_test_mode_disabled() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        assertEquals(Clock.systemUTC(), clock());
    }

    @Test
    void should_calculate_current_instant_with_nanoseconds_precision_with_test_mode_disabled_in_jdk_11() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyNanosecondsResolution(() -> instant().getNano());
    }

    @Test
    void should_calculate_current_local_date_time_with_nanoseconds_precision_with_test_mode_disabled_in_jdk_11() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyNanosecondsResolution(() -> dateTime().getNano());
    }

    @Test
    void should_calculate_current_offset_date_time_with_nanoseconds_precision_with_test_mode_disabled_in_jdk_11() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyNanosecondsResolution(() -> offsetDateTime().getNano());
    }

    @Test
    void should_calculate_current_zoned_date_time_with_nanoseconds_precision_with_test_mode_disabled_in_jdk_11() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyNanosecondsResolution(() -> zonedDateTime().getNano());
    }

    @Test
    void should_calculate_current_local_time_with_nanoseconds_precision_with_test_mode_disabled_in_jdk_11() {
        assumeFalse(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyNanosecondsResolution(() -> time().getNano());
    }

    @Test
    void should_return_a_different_clock_reference_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        assertNotSame(Clock.systemUTC(), clock());
    }

    @Test
    void should_calculate_current_instant_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> instant().getNano());
    }

    @Test
    void should_calculate_current_local_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> dateTime().getNano());
    }

    @Test
    void should_calculate_current_offset_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> offsetDateTime().getNano());
    }

    @Test
    void should_calculate_current_zoned_date_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(()->zonedDateTime().getNano());
    }

    @Test
    void should_calculate_current_time_with_milliseconds_precision_with_test_mode_enabled() {
        assumeTrue(Boolean.valueOf(System.getProperties().getProperty("TEST", FALSE.toString())));
        verifyMillisecondsResolution(() -> time().getNano());
    }

    private void verifyMillisecondsResolution(final IntSupplier nanosecondsSupplier) {
        int successful = 0;
        for (int i = 0; i < 3; i++) {
            if (hasMillisecondPrecision(nanosecondsSupplier.getAsInt())) successful ++;

        }
        assertEquals(3, successful);
    }

    private void verifyNanosecondsResolution(final IntSupplier nanosecondsSupplier) {
        int successful = 0;
        for (int i = 0; i < 3; i++) {
            if (!hasMillisecondPrecision(nanosecondsSupplier.getAsInt())) successful ++;

        }
        assertTrue(successful > 0);
    }

    private boolean hasMillisecondPrecision(final int nanos) {
        return String.valueOf(nanos).replace("0", "").length() <= 3;
    }
}
