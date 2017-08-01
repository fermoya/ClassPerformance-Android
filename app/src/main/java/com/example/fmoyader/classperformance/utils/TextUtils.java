package com.example.fmoyader.classperformance.utils;

/**
 * Created by fmoyader on 31/7/17.
 */

public class TextUtils {

    public static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotEmpty(String... strings) {
        return !isEmpty(strings);
    }

}
