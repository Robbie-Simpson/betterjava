package com.revolut.butter;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.revolut.butter.MapStreamUtil.entriesToMap;
import static com.revolut.butter.MapStreamUtil.ifEntryKey;
import static com.revolut.butter.MapStreamUtil.ifEntryValue;
import static com.revolut.butter.MapStreamUtil.transformEntry;
import static org.assertj.core.api.Assertions.assertThat;

class MapStreamUtilTest {

    @Test
    public void should_convert_entries_to_map() {
        // given
        Map<String, Integer> givenMap = mapOf("a", 1, "b", 2, "c", 3);

        // when
        Map<String, Integer> actualMap = givenMap.entrySet().stream().collect(entriesToMap());

        // then

        assertThat(givenMap).isEqualTo(actualMap);
    }

    @Test
    public void should_filter_by_entry_key() {
        // given
        Map<Integer, String> givenMap = mapOf(-10, "minus_ten", -5, "minus_five", 11, "eleven");

        // when
        Map<Integer, String> filteredMap = givenMap.entrySet().stream()
            .filter(ifEntryKey(it -> it > 0))
            .collect(entriesToMap());

        // then
        assertThat(filteredMap)
            .hasSize(1)
            .containsEntry(11, "eleven");
    }

    @Test
    public void should_filter_by_value_key() {
        // given
        Map<String, Integer> givenMap = mapOf("minus_ten", -10, "minus_five", -5, "eleven", 11);

        // when
        Map<String, Integer> filteredMap = givenMap.entrySet().stream()
            .filter(ifEntryValue(it -> it > 0))
            .collect(entriesToMap());

        // then
        assertThat(filteredMap)
            .hasSize(1)
            .containsEntry("eleven", 11);
    }

    @Test
    public void should_convert_entries_to_map_with_value_mapper() {
        // given
        Map<String, String> map = mapOf(
            "1", "false",
            "2", "true",
            "3", "false");

        // when
        Map<String, Boolean> newMap = map.entrySet().stream()
            .collect(entriesToMap(Boolean::valueOf));

        // then
        assertThat(newMap).isEqualTo(mapOf(
            "1", false,
            "2", true,
            "3", false)
        );
    }

    @Test
    public void should_transform_entry() {
        // given
        Map<String, String> map = mapOf(
            "1", "false",
            "2", "true",
            "3", "false");

        // when
        Map<Integer, Boolean> newMap = map.entrySet().stream()
            .map(transformEntry(Integer::parseInt, Boolean::parseBoolean))
            .collect(entriesToMap());

        // then
        assertThat(newMap).isEqualTo(mapOf(
            1, false,
            2, true,
            3, false));
    }

    private <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        final HashMap<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }
}