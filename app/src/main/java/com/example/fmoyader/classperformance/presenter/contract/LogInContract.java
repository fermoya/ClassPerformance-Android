package com.example.fmoyader.classperformance.presenter.contract;

import android.content.Intent;

import com.example.fmoyader.classperformance.utils.Spinnable;

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

    interface View extends Spinnable {
        void onShowError();
        void onShowError(String errorMessage);
        void onStartActivityForResult(Intent intent, int requestCode);
        void onStartActivity(Intent intent);
        boolean dataIsNotEmpty();
        String getEmail();
        String getPassword();
    }
}
