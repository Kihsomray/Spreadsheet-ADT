package model;

public class Utility {

    public static boolean isAlphaNumerical(final char c) {
        return isNumerical(c) || isAlphabetical(c);
    }

    public static boolean isAlphabetical(final char c) {
        return isLowercase(c) || isUpperCase(c);
    }

    public static boolean isNumerical(final char c) {
        return c > 47 && c < 58;
    }

    public static boolean isLowercase(final char c) {
        return c > 96 && c < 123;
    }

    public static boolean isUpperCase(final char c) {
        return c > 64 && c < 91;
    }

}
