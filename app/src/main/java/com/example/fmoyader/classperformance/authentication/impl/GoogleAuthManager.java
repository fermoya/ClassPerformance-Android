package com.example.fmoyader.classperformance.authentication.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.utils.ErrorUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by fmoyader on 28/7/17.
 */

public class GoogleAuthManager extends AuthManager implements GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_CODE = 9001;
    private FragmentActivity activity;
    private GoogleApiClient googleApiClient;

    public GoogleAuthManager(AuthProcessListener listener, Context context, FragmentActivity activity) {
        super(context, listener, AuthProvider.GOOGLE_API);
        this.activity = activity;

        this.googleApiClient = createGoogleApiClient();
    }

    private GoogleApiClient createGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(BuildConfig.SERVER_KEY_ID)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        return googleApiClient;
    }

    @Override
    public void logIn() {
        if (googleApiClient.isConnected()) {
            Auth.GoogleSignInApi.silentSignIn(googleApiClient).setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    if (result.isSuccess()) {
                        logInFirebase(result.getSignInAccount());
                    } else if (result.getStatus().isCanceled()) {
                        listener.onLogInCancel();
                    } else {
                        String errorMessage = ErrorUtils.defaultErrorMessage(context);
                        listener.onLogInError(errorMessage);
                    }
                }
            });
        } else {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            activity.startActivityForResult(signInIntent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                logInFirebase(result.getSignInAccount());
            } else {
                String errorMessage = ErrorUtils.defaultErrorMessage(context);
                listener.onLogInError(errorMessage);
            }
        }
    }

    public void logInFirebase(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        super.logInFirebase(credential);
    }

    @Override
    public void logOutProvider() {
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Auth.GoogleSignInApi.signOut(googleApiClient);
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        listener.onLogInError(connectionResult.getErrorMessage());
    }

}
