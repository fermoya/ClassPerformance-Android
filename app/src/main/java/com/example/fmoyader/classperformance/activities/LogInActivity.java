package com.example.fmoyader.classperformance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenters.LogInContract;
import com.example.fmoyader.classperformance.presenters.LogInPresenter;
import com.example.fmoyader.classperformance.presenters.LogInView;

public class LogInActivity extends AppCompatActivity implements LogInContract.View{

    private LogInContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        presenter = new LogInPresenter(this);
    }

    public void logInFacebook(View v) {
        if (presenter != null) {
            presenter.logInFacebook();
        }
    }

    public void logInTwitter(View v) {
        if (presenter != null) {
            presenter.logInTwitter();
        }
    }

    public void logInGoogle(View v) {
        if (presenter != null) {
            presenter.logInGoogle();
        }
    }
}
