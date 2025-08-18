package com.niklas.playground.base52.code;

public class AliasName {
    private static final int LENGTH = 4;

    private static final char CHAR_LOW = 'a';
    private static final char CHAR_HIGH = 'Z';

    private static final long MIN = 0L;
    private static final long MAX = Base52.toBase10(String.valueOf(CHAR_HIGH).repeat(LENGTH));

    /** The lowest valid {@code AliasName} which is {@code aaaa} */
    public static final String LOWEST = String.valueOf(CHAR_LOW).repeat(LENGTH);

    /** The highest valid {@code AliasName} which is {@code ZZZZ} */
    public static final String HIGHEST = String.valueOf(CHAR_HIGH).repeat(LENGTH);


    /**
     * Translates a number into a {@link Base52} formatted string, but left padded with 'a'
     *
     * @param value must be within range {@code 0 <= value <= 7311615}
     * @return a string with length 4 containing only a-zA-Z characters
     */
    public static String from(long value) {
        if (value < MIN || value > MAX) {
            throw new IllegalArgumentException(
                    "Value: " + value + " is out of range (" + MIN + ", " + MAX + ")");
        }
        var base52 = Base52.toBase52(value);
        return String.valueOf(CHAR_LOW).repeat(LENGTH - base52.length()) + base52;
    }

    /**
     * Just a wrapper around {@link Base52#toBase10(String)}, but makes sure the value range is valid
     *
     * @param value a valid {@link Base52} with length 4
     * @return the Base 10 representation of the provided Base 52 string
     */
    static long toBase10(String value) {
        if (value == null || value.length() != LENGTH) {
            throw new IllegalArgumentException(
                    "Value: " + value + " has wrong length (must be " + LENGTH + ")");
        }
        return Base52.toBase10(value);
    }

    /**
     * 'Increments' the given {@code AliasName} string by 1. As every {@code AliasName} is a valid
     * {@code Base52} it's {@link Base52#increment(String)} method is used internally.
     *
     * @param value a valid {@link Base52} with length 4
     * @return the provided string incremented by 1 (e.g. aghZ -> agia)
     */
    public static String getNextName(String value) {
        if (HIGHEST.equals(value)) {
            throw new IllegalArgumentException("Result would be larger than max value " + HIGHEST);
        }

        return from(toBase10(value) + 1);
    }
}
