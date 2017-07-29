package com.example.fmoyader.classperformance.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.fmoyader.classperformance.activitiy.CoursesActivity;
import com.example.fmoyader.classperformance.activitiy.LogInActivity;
import com.example.fmoyader.classperformance.activitiy.SignUpActivity;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.authentication.impl.EmailAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.FacebookAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.GoogleAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.TwitterAuthManager;
import com.example.fmoyader.classperformance.presenter.contract.LogInContract;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by fmoyader on 25/7/17.
 */

public class LogInPresenter implements LogInContract.Presenter, GoogleApiClient.OnConnectionFailedListener, AuthProcessListener {

    private final LogInContract.View logInView;
    private final Context context;
    private AuthManager authManager;

    public LogInPresenter(Context context, LogInContract.View logInView) {
        this.logInView = logInView;
        this.context = context;
    }

    @Override
    public void onCreate() {
        if (AuthManager.isConnected()) {
            Intent intentToCoursesActivity = new Intent(context, CoursesActivity.class);
            logInView.onStartActivity(intentToCoursesActivity);
        }
    }

    @Override
    public void onLogInWithFacebook() {
        logInView.onStartLoader();
        authManager = new FacebookAuthManager(this, context, (LogInActivity) logInView);
        authManager.logIn();
    }

    @Override
    public void onLogInWithTwitter() {
        logInView.onStartLoader();
        authManager = new TwitterAuthManager(this, context, (LogInActivity) logInView);
        authManager.logIn();
    }

    @Override
    public void onLogInWithGoogle() {
        logInView.onStartLoader();
        authManager = new GoogleAuthManager(this, context, (LogInActivity) logInView);
        authManager.logIn();
    }

    @Override
    public void onLogInWithEmail() {
        //TODO: AuthManager Email
        logInView.onStartLoader();
        if (logInView.dataIsNotEmpty()) {
            String email = logInView.getEmail();
            String password = logInView.getPassword();
            authManager = new EmailAuthManager(context, this, email, password, (LogInActivity) logInView);
            authManager.logIn();
        }
    }

    @Override
    public void onSignUpWithEmail() {
        Intent intentToSignUpActivity = new Intent(context, SignUpActivity.class);
        logInView.onStartActivity(intentToSignUpActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        authManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        logInView.onStopLoader();
        logInView.onShowError(connectionResult.getErrorMessage());
    }

    @Override
    public void onLogInSuccess() {
        Intent intentToCourseActivity = new Intent(context, CoursesActivity.class);
        logInView.onStartActivity(intentToCourseActivity);
        logInView.onStopLoader();
    }

    @Override
    public void onLogInError(String errorMessage) {
        logInView.onShowError(errorMessage);
        logInView.onStopLoader();
    }

    @Override
    public void onLogInCancel() {
        logInView.onStopLoader();
    }
}
