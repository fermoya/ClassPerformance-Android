package com.example.fmoyader.classperformance.authentication.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.utils.ErrorUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by fmoyader on 29/7/17.
 */

public class EmailAuthManager extends AuthManager {
    private String email;
    private String password;
    private Activity activity;

    public EmailAuthManager() { }

    public EmailAuthManager(
            Context context, AuthProcessListener listener,
            String email, String password, Activity activity) {
        super(context, listener, AuthProvider.EMAIL_API);
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public void logIn() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        handleResponse(task);
                    }
                });
    }

    private void handleResponse(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            listener.onLogInSuccess();
        } else {
            String errorMessage = ErrorUtils.findErrorMessage(task.getException(), context);
            listener.onLogInError(errorMessage);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void logOutProvider() { }

    public void signUp() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        handleResponse(task);
                    }
                });
    }
}
