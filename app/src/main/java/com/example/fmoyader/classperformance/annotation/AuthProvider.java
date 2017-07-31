package com.example.fmoyader.classperformance.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.fmoyader.classperformance.annotation.AuthProvider.EMAIL_API;
import static com.example.fmoyader.classperformance.annotation.AuthProvider.FACEBOOK_API;
import static com.example.fmoyader.classperformance.annotation.AuthProvider.GOOGLE_API;
import static com.example.fmoyader.classperformance.annotation.AuthProvider.NONE;
import static com.example.fmoyader.classperformance.annotation.AuthProvider.TWITTER_API;

/**
 * Created by fmoyader on 28/7/17.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({FACEBOOK_API, GOOGLE_API, TWITTER_API, EMAIL_API, NONE})
public @interface AuthProvider {
    String FACEBOOK_API = "facebook.com";
    String GOOGLE_API = "google.com";
    String TWITTER_API = "twitter.com";
    String EMAIL_API = "email.com";
    String NONE = "";
}
