package com.example.fmoyader.classperformance.presenters;

import com.example.fmoyader.classperformance.activities.LogInActivity;

/**
 * Created by fmoyader on 25/7/17.
 */

public class LogInPresenterImpl implements LogInPresenter{

    private final LogInView logInView;

    public LogInPresenterImpl(LogInView logInView) {
        this.logInView = logInView;
    }

    @Override
    public void logInFacebook() {
        
    }

    @Override
    public void logInTwitter() {

    }

    @Override
    public void logInGoogle() {

    }
}
