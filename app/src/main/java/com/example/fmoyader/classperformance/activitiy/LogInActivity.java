package com.example.fmoyader.classperformance.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenter.LogInPresenter;
import com.example.fmoyader.classperformance.presenter.contract.LogInContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends SpinnerActivity implements LogInContract.View{

    private LogInContract.Presenter presenter;

    @BindView(R.id.edit_text_email) EditText emailEditText;
    @BindView(R.id.edit_text_password) EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ButterKnife.bind(this);
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
    public void onShowError() {
        String errorMessage = getString(R.string.error_unexpected_error);
        onShowError(errorMessage);
    }

    @Override
    public void onShowError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_dialog_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(R.string.alert_dialog_error_positive_button, null)
                .show();
    }

    @Override
    public void onStartActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public boolean dataIsNotEmpty() {
        String email = getEmail();
        String password = getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }
}
