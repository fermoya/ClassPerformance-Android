package com.example.fmoyader.classperformance.authentication.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.utils.ErrorUtils;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by fmoyader on 28/7/17.
 */

public class TwitterAuthManager extends AuthManager {

    private TwitterAuthClient twitterAuthClient;
    private Activity activity;

    public TwitterAuthManager() { }

    public TwitterAuthManager(AuthProcessListener listener, Context context, Activity activity) {
        super(context, listener, AuthProvider.TWITTER_API);
        this.activity = activity;
    }

    @Override
    public void logIn() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            logInFirebase(session.getAuthToken());
        } else {
            twitterAuthClient = new TwitterAuthClient();
            twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    logInFirebase(result.data.getAuthToken());
                }

                @Override
                public void failure(TwitterException exception) {
                    String errorMessage = ErrorUtils.findErrorMessage(exception, context);
                    listener.onLogInError(errorMessage);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == twitterAuthClient.getRequestCode()) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void logInFirebase(TwitterAuthToken authToken) {
        AuthCredential credential = TwitterAuthProvider.getCredential(authToken.token, authToken.secret);
        super.logInFirebase(credential);
    }

    @Override
    public void logOutProvider() {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }

}
