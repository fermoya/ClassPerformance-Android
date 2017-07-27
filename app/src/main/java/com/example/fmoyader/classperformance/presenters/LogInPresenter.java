package com.example.fmoyader.classperformance.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.activities.LogInActivity;
import com.example.fmoyader.classperformance.activities.SignUpActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

/**
 * Created by fmoyader on 25/7/17.
 */

public class LogInPresenter implements LogInContract.Presenter, GoogleApiClient.OnConnectionFailedListener {

    private final LogInContract.View logInView;
    private final Context context;

    private CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private TwitterAuthClient twitterAuthClient;

    private static final int RC_SIGN_IN_GOOGLE = 9001;
    private static final int RC_SIGN_UP_EMAIL = 8001;

    public LogInPresenter(Context context, LogInContract.View logInView) {
        this.logInView = logInView;
        this.context = context;
    }

    @Override
    public void onCreate() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            logInView.onLogInSuccessful();
        }
    }

    @Override
    public void onLogInWithFacebook() {
        logInView.onStartLoader();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            onLogInWithFacebookSuccess(accessToken);
        } else {
            configureFacebookCallback();
            LoginManager.getInstance().logInWithReadPermissions(
                    (LogInActivity) logInView, Arrays.asList("public_profile", "email", "user_friends"));
        }
    }

    private void configureFacebookCallback() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onLogInWithFacebookSuccess(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                       onLogInWithFacebookCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        onLogInWithFacebookError(exception);
                    }
                }
        );
    }

    @Override
    public void onLogInWithTwitter() {
        logInView.onStartLoader();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            TwitterAuthToken authToken = session.getAuthToken();
            onLogInWithTwitterSuccess(authToken);
        } else {
            configureTwitterCallback();
        }
    }

    private void configureTwitterCallback() {
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize((LogInActivity) logInView, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                onLogInWithTwitterSuccess(result.data.getAuthToken());
            }

            @Override
            public void failure(TwitterException exception) {
                onLogInWithTwitterError(exception);
            }
        });
    }

    @Override
    public void onLogInWithGoogle() {
        logInView.onStartLoader();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(BuildConfig.SERVER_KEY_ID)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((LogInActivity) logInView, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (!googleApiClient.isConnected()) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            logInView.onStartActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
        } else {
            Auth.GoogleSignInApi.silentSignIn(googleApiClient).setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    if (result.isSuccess()) {
                        onLogInWithGoogleSuccess(result.getSignInAccount());
                    } else if (result.getStatus().isCanceled()) {
                        onLogInWithGoogleCancel();
                    } else {
                        onLogInWithGoogleError();
                    }
                }
            });
        }
    }

    @Override
    public void onLogInWithEmail() {
        logInView.onStartLoader();

    }

    @Override
    public void onSignUpWithEmail() {
        Intent intentToSignUpActivity = new Intent(context, SignUpActivity.class);
        logInView.onStartActivityForResult(intentToSignUpActivity, RC_SIGN_UP_EMAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                onLogInWithGoogleSuccess(result.getSignInAccount());
            } else {
                onLogInWithGoogleError();
            }
        } else if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == twitterAuthClient.getRequestCode()) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onLogInWithGoogleSuccess(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        signInSocialNetwork(credential);
    }

    private void onLogInWithGoogleError() {
        logInView.onStopLoader();
        logInView.onLogInError();
    }

    private void onLogInWithGoogleCancel() {
        logInView.onStopLoader();
    }

    private void onLogInWithTwitterSuccess(TwitterAuthToken authToken) {
        AuthCredential credential = TwitterAuthProvider.getCredential(authToken.token, authToken.secret);
        signInSocialNetwork(credential);
    }

    private void onLogInWithTwitterError(TwitterException exception) {
        String errorMessage = findErrorMessage(exception);
        logInView.onStopLoader();
        logInView.onLogInError(errorMessage);
    }

    private void onLogInWithFacebookSuccess(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        signInSocialNetwork(credential);
    }

    private void onLogInWithFacebookError(FacebookException exception) {
        String errorMessage = findErrorMessage(exception);
        logInView.onLogInError(errorMessage);
        logInView.onStopLoader();
    }

    private void onLogInWithFacebookCancel() {
        logInView.onStopLoader();
    }

    public void signInSocialNetwork(AuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    logInView.onLogInSuccessful();
                } else {
                    String errorMessage = findErrorMessage(task.getException());
                    logInView.onLogInError(errorMessage);
                }

                logInView.onStopLoader();
            }
        });
    }

    private String findErrorMessage(Exception exception) {
        return exception != null && exception.getMessage() != null
                && !exception.getMessage().isEmpty() ? exception.getMessage() :
                context.getString(R.string.alert_dialog_error_unexpected_error);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        logInView.onStopLoader();
        logInView.onLogInError(connectionResult.getErrorMessage());
    }
}
