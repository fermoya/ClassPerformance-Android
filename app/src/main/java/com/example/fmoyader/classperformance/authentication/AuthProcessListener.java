package com.example.fmoyader.classperformance.authentication;

import com.google.firebase.auth.AuthCredential;

/**
 * Created by fmoyader on 28/7/17.
 */

public interface AuthProcessListener {
    void onLogInSuccess();
    void onLogInError(String errorMessage);
    void onLogInCancel();
}
