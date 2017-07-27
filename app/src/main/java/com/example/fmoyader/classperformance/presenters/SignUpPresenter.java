package com.example.fmoyader.classperformance.presenters;

import com.example.fmoyader.classperformance.activities.SignUpActivity;

/**
 * Created by fmoyader on 27/7/17.
 */

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View signUpView;

    public SignUpPresenter(SignUpContract.View signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void onSignUp() {
        signUpView.checkData();
    }
}
