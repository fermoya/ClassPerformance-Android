package com.example.fmoyader.classperformance.activitiy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenter.contract.SignUpContract;
import com.example.fmoyader.classperformance.presenter.SignUpPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends SpinnerActivity implements SignUpContract.View {

    @BindView(R.id.edit_text_email) EditText emailEditText;
    @BindView(R.id.edit_text_password) EditText passwordEditText;
    @BindView(R.id.edit_text_confirm_password) EditText confirmPasswordEditText;

    private SignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        presenter = new SignUpPresenter(this, this);
    }

    public void onSignUp (View view) {
        presenter.onSignUp();
    }

    @Override
    public boolean dataIsNotEmpty() {
        String email = getEmail();
        String password = getPassword();
        String passwordConfirmation = getPasswordConfirmation();

        if (email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean passwordIsValid() {
        String password = getPassword();
        String passwordConfirmation = getPasswordConfirmation();

        if (!password.equals(passwordConfirmation)) {
            return false;
        } else {
            return true;
        }
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
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public String getPasswordConfirmation() {
        return confirmPasswordEditText.getText().toString();
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }
}
