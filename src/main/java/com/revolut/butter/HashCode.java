package com.revolut.butter;

import java.util.Objects;

/**
 * A collection of overloaded methods that allow to generate hash codes efficiently
 * with zero work for Garbage Collector.
 * <p>
 */
public class HashCode {

    public static int hash(Object o1, Object o2) {
        return add(Objects.hashCode(o1), o2);
    }

    public static int hash(Object o1, Object o2, Object o3) {
        return hash(Objects.hashCode(o1), o2, o3);
    }

    public static int hash(Object o1, Object o2, Object o3, Object o4) {
        return hash(Objects.hashCode(o1), o2, o3, o4);
    }

    public static int hash(Object o1, Object o2, Object o3, Object o4, Object o5) {
        return hash(Objects.hashCode(o1), o2, o3, o4, o5);
    }

    public static int hash(Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        return hash(Objects.hashCode(o1), o2, o3, o4, o5, o6);
    }

    public static int hash(Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        return hash(Objects.hashCode(o1), o2, o3, o4, o5, o6, o7);
    }

    public static int hash(Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        return hash(Objects.hashCode(o1), o2, o3, o4, o5, o6, o7, o8);
    }

    public static int hash(int initial, Object o1) {
        return add(initial, o1);
    }

    public static int hash(int initial, Object o1, Object o2) {
        return add(add(initial, o1), o2);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3) {
        return add(add(add(initial, o1), o2), o3);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4) {
        return add(add(add(add(initial, o1), o2), o3), o4);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5) {
        return add(add(add(add(add(initial, o1), o2), o3), o4), o5);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        return add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        return add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        return add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        return add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
        return add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11) {
        return add(add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10), o11);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12) {
        return add(add(add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10), o11), o12);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12, Object o13) {
        return add(add(add(add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10), o11), o12), o13);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12, Object o13, Object o14) {
        return add(add(add(add(add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10), o11), o12), o13), o14);
    }

    public static int hash(int initial, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12, Object o13, Object o14, Object o15) {
        return add(add(add(add(add(add(add(add(add(add(add(add(add(add(add(initial, o1), o2), o3), o4), o5), o6), o7), o8), o9), o10), o11), o12), o13), o14), o15);
    }

    public static int hash(long value) {
        return (int) (value ^ (value >>> 32));
    }

    public static int hash(int initial, long value) {
        return add(initial, hash(value));
    }

    public static int hash(int initial, double value) {
        return add(initial, hash(value));
    }

    public static int hash(boolean value) {
        return value ? 1 : 0;
    }

    public static int hash(double value) {
        return Double.hashCode(value);
    }

    public static int hash(int initial, boolean value) {
        return add(initial, hash(value));
    }

    private static int add(int accumulator, Object o) {
        return 31 * accumulator + Objects.hashCode(o);
    }
}
