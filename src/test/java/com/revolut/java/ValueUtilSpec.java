package com.revolut.java;

import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.revolut.java.BetterOptional.*;
import static com.revolut.java.ValueUtil.*;
import static com.revolut.java.ValueUtilSpec.DeliveryStatus.POSTED;
import static com.revolut.java.ValueUtilSpec.Repo.FIRST;
import static com.revolut.java.ValueUtilSpec.Repo.SECOND;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ValueUtilSpec {

    @Test
    void takeWhile_should_iterate_over_stream_until_predicate_is_false() {
        final Supplier<Stream<Integer>> supplier = () -> Stream.of(1, 2, 3, 4, 5);

        assertThat(takeWhile(supplier.get(), i -> i < 4).collect(toList())).containsSequence(1, 2, 3);
        assertThat(takeWhile(supplier.get(), i -> i > 0).collect(toList())).isEqualTo(supplier.get().collect(toList()));
        assertThat(takeWhile(supplier.get(), i -> i < 0).collect(toList())).isEmpty();
    }

    @Test
    void decimal_from_string() {
        assertThat(decimal("12.34")).isEqualTo(new BigDecimal("12.34"));
    }

    @Test
    void decimal_from_double() {
        assertThat(decimal(12.345)).isEqualTo(BigDecimal.valueOf(12.345));
    }

    @Test
    void decimal_from_long() {
        assertThat(decimal(123456L)).isEqualTo(new BigDecimal(123456L));
    }

    @Test
    void get_first_not_empty_from_optionals() {
        assertThat(some("123"))
            .isEqualTo(firstNonEmpty(absent(), optional(null), some("123")));
    }

    @Test
    void get_first_not_empty_from_optional_suppliers() {
        assertThat(some("123"))
            .isEqualTo(firstNonEmpty(BetterOptional::absent, () -> optional(null), () -> some("123")));
    }

    @Test
    void get_enum_value_if_present() {
        assertThat(POSTED)
            .isEqualTo(enumValueOf("POSTED", DeliveryStatus.class));
    }

    @Test
    void get_enum_value_if_null() {
        assertThat(enumValueOf(null, DeliveryStatus.class))
            .isNull();
    }

    @Test
    void get_optional_enum_value_if_present() {
        assertThat(some(POSTED))
            .isEqualTo(optionValueOf("POSTED", DeliveryStatus.class));
    }

    @Test
    void get_optional_enum_value_if_absent() {
        assertThat(absent()).isEqualTo(optionValueOf("DOES_NOT_EXIST", DeliveryStatus.class));
    }

    @Test
    void creates_url() throws MalformedURLException {
        String urlString = "http://localhost:8080";

        assertThat(url(urlString))
            .isEqualTo(new URL(urlString));
    }

    @Test
    void throws_IllegalArgumentException_for_malformed_url() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> url("abcd"))
            .withMessage("Malformed URL: abcd");
    }

    @Test
    void creates_empty_list_of_given_type() {
        assertThat(emptyListOf(String.class))
            .isEqualTo(emptyList());
    }

    @Test
    void creates_empty_set_of_given_type() {
        assertThat(emptySetOf(String.class))
            .isEqualTo(emptySet());
    }

    @Test
    void creates_empty_map_of_given_type() {
        assertThat(emptyMapOf(String.class, String.class))
            .isEqualTo(emptyMap());
    }

    @Test
    void memoizing_first_item() {
        Repo repoBefore = new Repo();
        assertThat(repoBefore.get()).isEqualTo(FIRST);
        assertThat(repoBefore.get()).isEqualTo(SECOND);

        Repo repo = new Repo();
        Supplier<String> valueSupplier = memoizing(repo::get);

        assertThat(valueSupplier.get()).isEqualTo(FIRST);
        assertThat(valueSupplier.get()).isEqualTo(FIRST);
    }

    @Test
    void creates_ordered_map() {
        Map<String, Integer> map = Stream.of("B", "CA", "AED", "E")
            .collect(toOrderedMap(letter -> letter, String::length));

        assertThat(map).containsExactly(
            MapEntry.entry("B", 1),
            MapEntry.entry("CA", 2),
            MapEntry.entry("AED", 3),
            MapEntry.entry("E", 1)
        );
    }

    class Repo {
        static final String FIRST = "first";
        static final String SECOND = "second";

        private AtomicBoolean nonFirstRequest = new AtomicBoolean(false);

        String get() {
            return nonFirstRequest.getAndSet(true) ? SECOND : FIRST;
        }
    }

    enum DeliveryStatus {
        POSTED
    }
}