package com.niklas.playground.base52.code;

import java.util.HashMap;
import java.util.Map;

public class Base52 {
    // The Goal is to convert a number in base 10 into a number in base 52 and vice versa.
    // Base 52 uses the characters a-z and A-Z, so it has 52

    // e.g. aaaa = 0, aaab = 1

    public static final String MAX_VALUE = "blPTsbvaOoEh";

    private static final Map<Character, Integer> CHAR_TO_INT = new HashMap<>();
    private static final Map<Integer, Character> INT_TO_CHAR = new HashMap<>();
    private static final int ALPHABET_SIZE = 26;
    private static final int BASE52_ALPHABET_SIZE = 2 * ALPHABET_SIZE;

    static {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            CHAR_TO_INT.put((char) ('a' + i), i);
            CHAR_TO_INT.put((char) ('A' + i), i + ALPHABET_SIZE);

            INT_TO_CHAR.put(i, (char) ('a' + i));
            INT_TO_CHAR.put(i + ALPHABET_SIZE, (char) ('A' + i));
        }
    }

    public static String toBase52(final long base10) {
        if (base10 < 0) {
            throw new IllegalArgumentException("Negative values are not allowed");
        }

        /* Use the faster version */
        if (base10 == 0) {
            return "a";
        }

        StringBuilder sb = new StringBuilder();
        long i = base10;

        while (i >= BASE52_ALPHABET_SIZE) {
            var posInAlphabet = (int) (i % BASE52_ALPHABET_SIZE);
            sb.append(INT_TO_CHAR.get(posInAlphabet));
            i /= BASE52_ALPHABET_SIZE;
        }
        sb.append(INT_TO_CHAR.get((int) i));

        return sb.reverse().toString();
    }

    public static long toBase10(final String base52) {
        if (base52 == null
                || base52.chars().anyMatch(c -> c < 'A' || ('Z' < c && c < 'a') || 'z' < c)) {
            throw new IllegalArgumentException("Base52 string is not valid (allowed: a-zA-Z)");
        }

        long base10 = 0L;
        for (int i = 0; i < base52.length(); i++) {
            var digits = base52.charAt(base52.length() - 1 - i);
            base10 += CHAR_TO_INT.get(digits) * (long) Math.pow(BASE52_ALPHABET_SIZE, i);
        }
        return base10;
    }

    public static String increment(final String base52) {
        if (compare(MAX_VALUE, base52) <= 0) {
            throw new IllegalArgumentException("Result would be larger than max value " + MAX_VALUE);
        }

        return toBase52(toBase10(base52) + 1);
    }

    static int compare(final String value, final String anotherValue) {
        if (value == null || anotherValue == null) {
            throw new IllegalArgumentException("null values are not allowed");
        }

        if (value.equals(anotherValue)) {
            return 0;
        }

        int value1Length = value.length();
        int anotherValueLength = anotherValue.length();
        if (value1Length != anotherValueLength) {
            return value1Length - anotherValueLength;
        } else {
            char[] value1 = value.toCharArray();
            char[] anotherValue1 = anotherValue.toCharArray();
            for (int i = 0; i < value1Length; i++) {
                char c1 = value1[i];
                char c2 = anotherValue1[i];
                if (c1 == c2) {
                    continue;
                }
                if (Character.isLowerCase(c1) && Character.isUpperCase(c2)
                        || Character.isUpperCase(c1) && Character.isLowerCase(c2)) {
                    return c2 - c1;
                }
                return c1 - c2;
            }
        }

        // if we reach this line, something went wrong
        throw new UnknownError("Unable to compare values");
    }

}
