package com.revolut.java;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.revolut.java.BetterOptional.optional;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toMap;

public class ValueUtil {

    public static BigDecimal decimal(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal decimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal decimal(long value) {
        return new BigDecimal(value);
    }

    @SafeVarargs
    public static <T> Optional<T> firstNonEmpty(Optional<T>... options) {
        return stream(options).filter(Optional::isPresent).findFirst().orElse(empty());
    }

    @SafeVarargs
    public static <T> Optional<T> firstNonEmpty(Supplier<Optional<T>>... options) {
        return stream(options)
            .map(Supplier::get)
            .filter(Optional::isPresent)
            .findFirst()
            .orElse(empty());
    }

    public static <T extends Enum<T>> T enumValueOf(String value, Class<T> enumType) {
        if (value == null) return null;
        return Enum.valueOf(enumType, value);
    }

    public static <T extends Enum<T>> Optional<T> optionValueOf(String value, Class<T> enumType) {
        try {
            return optional(value).map(v -> Enum.valueOf(enumType, value));
        } catch (IllegalArgumentException e) {
            return empty();
        }
    }

    public static URL url(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + urlString);
        }
    }

    public static <T> List<T> emptyListOf(@SuppressWarnings("unused") Class<T> unused) {
        return Collections.emptyList();
    }

    public static <T> Set<T> emptySetOf(@SuppressWarnings("unused") Class<T> unused) {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> emptyMapOf(@SuppressWarnings("unused") Class<K> key, @SuppressWarnings("unused") Class<V> value) {
        return Collections.emptyMap();
    }

    public static <T> Supplier<T> memoizing(Supplier<T> supplier) {
        return new Supplier<T>() {
            private T memoized;

            @Override
            public T get() {
                if (memoized == null) {
                    memoized = supplier.get();
                }
                return memoized;
            }
        };
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toOrderedMap(Function<? super T, ? extends K> keyMapper,
                                                                    Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper,
            (u, v) -> {
                throw new IllegalStateException(String.format("Duplicate key %s", u));
            },
            LinkedHashMap::new);
    }

    // Fixme: remove after Java 10 migration is complete
    public static <T> Stream<T> takeWhile(Stream<T> source, Predicate<T> condition) {
        return StreamSupport.stream(TakeWhileSpliterator.over(source, condition), false)
            .onClose(source::close);
    }

    private static class TakeWhileSpliterator<T> implements Spliterator<T> {

        static <T> TakeWhileSpliterator<T> over(Stream<T> source, Predicate<T> condition) {
            return new TakeWhileSpliterator<>(source.spliterator(), condition);
        }

        private final Spliterator<T> source;
        private final Predicate<T> condition;

        private boolean stop;

        private TakeWhileSpliterator(Spliterator<T> source, Predicate<T> condition) {
            this.source = source;
            this.condition = condition;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            return !stop && source.tryAdvance(e -> {
                if (condition.test(e)) {
                    action.accept(e);
                } else
                    stop = true;
            });
        }

        @Override
        public Spliterator<T> trySplit() {
            return source.trySplit();
        }

        @Override
        public long estimateSize() {
            return source.estimateSize();
        }

        @Override
        public int characteristics() {
            return source.characteristics();
        }

        @Override
        public Comparator<? super T> getComparator() {
            return source.getComparator();
        }
    }

    private ValueUtil() {
        throw new AssertionError();
    }
}
