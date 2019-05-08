package com.revolut.micro;

import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class MicrotypeSpec {

    @Test
    void captures_underlying_primitive() {
        int num = 42;
        assertThat(FavouriteNumber.of(num).value).isEqualTo(num);
    }

    @Test
    void allows_to_use_value_in_lambda() {
        EntityId entityId1 = EntityId.of(randomUUID());
        EntityId entityId2 = EntityId.of(randomUUID());
        List<EntityId> entityIds = Arrays.asList(entityId1, entityId2);

        List<UUID> ids = entityIds.stream().map(Microtype::value).collect(toList());

        assertThat(ids).containsExactlyInAnyOrder(entityId1.value, entityId2.value);
    }

    @Test
    void two_microtypes_sharing_same_underlying_object_are_equal() {
        UUID id = randomUUID();

        assertThat(EntityId.of(id)).isEqualTo(EntityId.of(id));
    }

    @Test
    void two_microtypes_sharing_same_underlying_object_generate_same_hashcode() {
        UUID id = randomUUID();

        assertThat(EntityId.of(id).hashCode()).isEqualTo(EntityId.of(id).hashCode());
    }

    @Test
    void delegates_to_string() {
        UUID id = randomUUID();
        assertThat(EntityId.of(id).toString()).isEqualTo(id.toString());
    }

    @Test
    void throws_exception_if_null_passed() {
        assertThatNullPointerException()
            .isThrownBy(() -> EntityId.of(null))
            .withMessage("value");
    }

    static class EntityId extends Microtype<UUID> {
        EntityId(UUID value) {
            super(value);
        }

        static EntityId of(UUID value) {
           return new EntityId(value);
        }
    }

    static class FavouriteNumber extends Microtype<Integer> {

        FavouriteNumber(Integer value) {
            super(value);
        }

        static FavouriteNumber of(Integer value) {
            return new FavouriteNumber(value);
        }
    }
}