package com.revolut.micro;

import static java.util.Objects.requireNonNull;

public abstract class Microtype<T> {

    public final T value;

    protected Microtype(T value) {
        this.value = requireNonNull(value, "value");
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return value.equals(((Microtype) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
