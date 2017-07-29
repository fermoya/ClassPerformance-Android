package com.example.fmoyader.classperformance.utils;

import android.content.Context;

import com.example.fmoyader.classperformance.R;

/**
 * Created by fmoyader on 28/7/17.
 */

public class ErrorUtils {

    public static String findErrorMessage(Exception exception, Context context) {
        return exception != null && exception.getMessage() != null
                && !exception.getMessage().isEmpty() ? exception.getMessage() :
                defaultErrorMessage(context);
    }

    public static String defaultErrorMessage(Context context) {
        return context.getString(R.string.error_unexpected_error);
    }
}
