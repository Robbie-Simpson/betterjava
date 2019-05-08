package com.revolut.java;

import java.time.*;
import java.util.TimeZone;

public final class TimeProvider {

    private Clock clock;

    public static Instant instant() {
        return Instant.now(clock());
    }

    public static LocalDateTime dateTime() {
        return LocalDateTime.now(clock());
    }

    public static LocalDateTime dateTime(ZoneId zone) {
        return LocalDateTime.now(clock().withZone(zone));
    }

    public static OffsetDateTime offsetDateTime() {
        return OffsetDateTime.now(clock());
    }

    public static ZonedDateTime zonedDateTime() {
        return ZonedDateTime.now(clock());
    }

    public static LocalTime time() {
        return LocalTime.now(clock());
    }

    public static LocalDate date() {
        return LocalDate.now(clock());
    }

    public static LocalDate date(ZoneId zone) {
        return LocalDate.now(clock().withZone(zone));
    }

    public static Clock clock() {
        return TimeProviderHolder.INSTANCE.clock;
    }

    private static Clock defaultClock() {
        return Clock.systemUTC();
    }

    private static Clock millisClock() {
        return Clock.tick(Clock.systemUTC(), Duration.ofMillis(1));
    }

    private static boolean isMillisecondPrecisionEnabled() {
        return System.getProperties().getProperty("time.precision", "default").equals("milliseconds");
    }

    private TimeProvider() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        if (isMillisecondPrecisionEnabled()) {
            this.clock = millisClock();
        } else {
            this.clock = defaultClock();
        }
    }

    private static class TimeProviderHolder {
        private static final TimeProvider INSTANCE = new TimeProvider();
    }
}
