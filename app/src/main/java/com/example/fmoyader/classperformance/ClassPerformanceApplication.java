package com.example.fmoyader.classperformance;

import android.app.Application;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by fmoyader on 26/7/17.
 */

public class ClassPerformanceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_SECRET_KEY))
                .debug(true)
                .build();
        Twitter.initialize(config);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
