package com.example.fmoyader.classperformance.presenters;

import android.content.Intent;

/**
 * Created by fmoyader on 25/7/17.
 */

public interface LogInContract {
    interface Presenter {
        void onLogInWithFacebook();
        void onLogInWithTwitter();
        void onLogInWithGoogle();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void onCreate();
        void onLogInWithEmail();
        void onSignUpWithEmail();
    }

    interface View {
        void onLogInSuccessful();
        void onLogInError();
        void onLogInError(String errorMessage);
        void onStopLoader();
        void onStartLoader();
        void onStartActivityForResult(Intent signInIntent, int requestCode);
    }
}
