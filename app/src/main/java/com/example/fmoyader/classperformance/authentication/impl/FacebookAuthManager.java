package com.example.fmoyader.classperformance.authentication.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.utils.ErrorUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fmoyader on 28/7/17.
 */

public class FacebookAuthManager extends AuthManager {

    private CallbackManager callbackManager;
    private Activity activity;

    private static final List<String> PERMISSIONS = Arrays.asList("public_profile", "email", "user_friends");

    public FacebookAuthManager(AuthProcessListener listener, Context context, Activity activity) {
        super(context, listener, AuthProvider.FACEBOOK_API);
        this.activity = activity;

        callbackManager = createCallbackManager();
    }

    private CallbackManager createCallbackManager() {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        logInFirebase(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        listener.onLogInCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        String errorMessage = ErrorUtils.findErrorMessage(exception, context);
                        listener.onLogInError(errorMessage);
                    }
                }
        );
        return callbackManager;
    }

    public void logInFirebase(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        super.logInFirebase(credential);
    }

    @Override
    public void logIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            logInFirebase(accessToken);
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, PERMISSIONS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void logOutProvider() {
        LoginManager.getInstance().logOut();
    }
}
