package com.example.fmoyader.classperformance.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fmoyader on 28/7/17.
 */

public abstract class AuthManager {

    protected Context context;
    protected AuthProcessListener listener;
    protected final @AuthProvider String provider;

    public AuthManager(Context context, AuthProcessListener listener, @AuthProvider String provider) {
        this.context = context;
        this.listener = listener;
        this.provider = provider;
    }

    public abstract void logIn();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public static boolean isConnected() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser != null;
    }

    public @AuthProvider String getProvider() {
        return this.provider;
    }

    public void logInFirebase(AuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (listener != null) {
                    handleFirebaseResponse(task);
                }
            }
        });
    }

    private void handleFirebaseResponse(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            listener.onLogInSuccess();
        } else {
            logOutProvider();
            String errorMessage = findErrorMessage(task.getException());
            listener.onLogInError(errorMessage);
        }
    }

    private String findErrorMessage(Exception exception) {
        return exception != null && exception.getMessage() != null
                && !exception.getMessage().isEmpty() ? exception.getMessage() :
                context.getString(R.string.error_unexpected_error);
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        logOutProvider();
    }

    protected abstract void logOutProvider();

}
