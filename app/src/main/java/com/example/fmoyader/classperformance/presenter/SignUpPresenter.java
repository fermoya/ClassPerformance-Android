package com.example.fmoyader.classperformance.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.activitiy.CoursesActivity;
import com.example.fmoyader.classperformance.activitiy.SignUpActivity;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.authentication.impl.EmailAuthManager;
import com.example.fmoyader.classperformance.presenter.contract.SignUpContract;

/**
 * Created by fmoyader on 27/7/17.
 */

public class SignUpPresenter implements SignUpContract.Presenter, AuthProcessListener {

    private SignUpContract.View signUpView;
    private Context context;

    public SignUpPresenter(SignUpContract.View signUpView, Context context) {
        this.signUpView = signUpView;
        this.context = context;
    }

    @Override
    public void onSignUp() {
        if (signUpView.dataIsNotEmpty()) {
            if (signUpView.passwordIsValid()) {
                createUser();
            } else {
                signUpView.onShowError(context.getString(R.string.error_invalid_password_confirmation));
            }
        } else {
            signUpView.onShowError(context.getString(R.string.error_invalid_password_confirmation));
        }
    }

    private void createUser() {
        signUpView.onStartLoader();
        String email = signUpView.getEmail();
        String password = signUpView.getPassword();

        EmailAuthManager manager = new EmailAuthManager(
                context, this, email,
                password, (SignUpActivity) signUpView
        );

        manager.signUp();
    }

    @Override
    public void onLogInSuccess() {
        Intent intentToCoursesActivity = new Intent(context, CoursesActivity.class);
        signUpView.onStartActivity(intentToCoursesActivity);
        signUpView.onStopLoader();
    }

    @Override
    public void onLogInError(String errorMessage) {
        signUpView.onShowError(errorMessage);
        signUpView.onStopLoader();
    }

    @Override
    public void onLogInCancel() {
        signUpView.onStopLoader();
    }
}
