package com.example.fmoyader.classperformance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenters.LogInContract;
import com.example.fmoyader.classperformance.presenters.LogInPresenter;

public class LogInActivity extends AppCompatActivity implements LogInContract.View{

    private LogInContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        presenter = new LogInPresenter(this, this);
        presenter.onCreate();
    }

    public void onLogInWithFacebook(View v) {
        if (presenter != null) {
            presenter.onLogInWithFacebook();
        }
    }

    public void onLogInWithTwitter(View v) {
        if (presenter != null) {
            presenter.onLogInWithTwitter();
        }
    }

    public void onLogInWithGoogle(View v) {
        if (presenter != null) {
            presenter.onLogInWithGoogle();
        }
    }

    public void onLogInWithEmail(View v) {
        if (presenter != null) {
            presenter.onLogInWithEmail();
        }
    }

    public void onSignUpWithEmail(View v) {
        if (presenter != null) {
            presenter.onSignUpWithEmail();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLogInSuccessful() {
        Intent intentToCoursesActivity = new Intent(this, CoursesActivity.class);
        startActivity(intentToCoursesActivity);
    }

    @Override
    public void onLogInError() {
        String errorMessage = getString(R.string.alert_dialog_error_unexpected_error);
        onLogInError(errorMessage);
    }

    @Override
    public void onLogInError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_dialog_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(R.string.alert_dialog_error_positive_button, null)
                .show();
    }

    @Override
    public void onStopLoader() {

    }

    @Override
    public void onStartLoader() {

    }

    @Override
    public void onStartActivityForResult(Intent signInIntent, int requestCode) {
        startActivityForResult(signInIntent, requestCode);
    }
}
