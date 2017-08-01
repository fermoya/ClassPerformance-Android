package com.example.fmoyader.classperformance.presenter.contract;

import android.content.Intent;

import com.example.fmoyader.classperformance.utils.ErrorAlertDisplay;
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
        void onBackPressed();
    }

    interface View extends Spinnable, ErrorAlertDisplay {
        void onStartActivity(Intent intent);
        boolean dataIsNotEmpty();
        String getEmail();
        String getPassword();
    }
}
