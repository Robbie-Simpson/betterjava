package com.revolut.butter;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toMap;

public interface MapStreamUtil {

    static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> entriesToMap() {
        return toMap(Entry::getKey, Entry::getValue);
    }

    static <K, V, V2> Collector<Entry<K, V>, ?, Map<K, V2>> entriesToMap(Function<V, V2> valueMapper) {
        return toMap(Entry::getKey, entry -> valueMapper.apply(entry.getValue()));
    }

    static <K, V> Predicate<? super Entry<K, V>> ifEntryKey(Predicate<? super K> keyPredicate) {
        return entry -> keyPredicate.test(entry.getKey());
    }

    static <K, V> Predicate<? super Entry<K, V>> ifEntryValue(Predicate<? super V> valuePredicate) {
        return entry -> valuePredicate.test(entry.getValue());
    }

    static <K1, K2, V1, V2> Function<Entry<K1, V1>, Entry<K2, V2>> transformEntry(Function<K1, K2> keyMapper,
                                                                                  Function<V1, V2> valueMapper) {
        return entry -> new AbstractMap.SimpleImmutableEntry<>(
            keyMapper.apply(entry.getKey()),
            valueMapper.apply(entry.getValue()));
    }
}
