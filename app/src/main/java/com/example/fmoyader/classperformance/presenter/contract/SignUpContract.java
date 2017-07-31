package com.example.fmoyader.classperformance.presenter.contract;

import android.content.Intent;

import com.example.fmoyader.classperformance.utils.ErrorAlertDisplay;
import com.example.fmoyader.classperformance.utils.Spinnable;

/**
 * Created by fmoyader on 27/7/17.
 */

public interface SignUpContract {
    interface Presenter {
        void onSignUp();
    }

    interface View extends Spinnable, ErrorAlertDisplay {
        boolean dataIsNotEmpty();
        boolean passwordIsValid();
        String getEmail();
        String getPassword();
        String getPasswordConfirmation();
        void onStartActivity(Intent intent);
    }
}
