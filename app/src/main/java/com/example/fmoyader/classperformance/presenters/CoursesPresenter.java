package com.example.fmoyader.classperformance.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.activities.CoursesActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterCore;

/**
 * Created by fmoyader on 26/7/17.
 */

public class CoursesPresenter implements CoursesContract.Presenter, GoogleApiClient.OnConnectionFailedListener {

    private final Context context;
    private CoursesContract.View coursesView;
    private GoogleApiClient googleApiClient;

    public CoursesPresenter(CoursesContract.View coursesView, Context context) {
        this.coursesView = coursesView;
        this.context = context;
    }

    @Override
    public void onCreate() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getProviderData() != null && !currentUser.getProviderData().isEmpty()) {
                int lastIndex = currentUser.getProviderData().size() - 1;
                String providerId = currentUser.getProviderData().get(lastIndex).getProviderId();
                if (providerId.equals("google.com")) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            //.requestIdToken(BuildConfig.SERVER_KEY_ID)
                            .requestIdToken(context.getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();

                    googleApiClient = new GoogleApiClient.Builder(context)
                            .enableAutoManage((CoursesActivity) coursesView, this)
                            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                            .build();

                    googleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        coursesView.onShowLogOutDialog();
    }

    @Override
    public void onLogOut() {
        //TODO: logout logic
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getProviderData() != null && !currentUser.getProviderData().isEmpty()) {
                int lastIndex = currentUser.getProviderData().size() - 1;
                String providerId = currentUser.getProviderData().get(lastIndex).getProviderId();
                switch (providerId) {
                    case "google.com":
                        logOutWithGoogle();
                        break;
                    case "twitter.com":
                        logOutWithTwitter();
                        break;
                    case "facebook.com":
                        logOutWithFacebook();
                        break;
                    default:
                        break;
                }
            }
        }
        FirebaseAuth.getInstance().signOut();
    }

    private void logOutWithFacebook() {
        LoginManager.getInstance().logOut();
    }

    private void logOutWithTwitter() {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }

    private void logOutWithGoogle() {
        if (googleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(googleApiClient);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
