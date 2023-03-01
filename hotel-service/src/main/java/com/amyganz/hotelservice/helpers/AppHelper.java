package com.amyganz.hotelservice.helpers;

public class AppHelper {
    private static final String NON_NORMAL_CHARACTERS_PATTERN = "\\W|[^!@#\\$%\\^&\\*\\(\\)]";

    public static boolean hasSymbols(String string) {
        return string.matches(NON_NORMAL_CHARACTERS_PATTERN);
    }
}
