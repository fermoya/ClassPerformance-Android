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
    protected @AuthProvider String provider;

    public AuthManager() {  }

    public AuthManager(Context context, AuthProcessListener listener, @AuthProvider String provider) {
        this.context = context;
        this.listener = listener;
        this.provider = provider;
    }

    public static String getaAuthenticatedUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            return user.getUid();
        }

        return null;
    }

    public abstract void logIn();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public static boolean isConnected() {
        return getFirebaseUser() != null;
    }

    private static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static @AuthProvider String getProvider() {
        if (isConnected()) {
            FirebaseUser currentUser = getFirebaseUser();
            if (currentUser.getProviderData() != null && !currentUser.getProviderData().isEmpty()) {
                int lastIndex = currentUser.getProviderData().size() - 1;
                String providerId = currentUser.getProviderData().get(lastIndex).getProviderId();
                switch (providerId) {
                    case AuthProvider.GOOGLE_API:
                        return AuthProvider.GOOGLE_API;
                    case AuthProvider.TWITTER_API:
                        return AuthProvider.TWITTER_API;
                    case AuthProvider.FACEBOOK_API:
                        return AuthProvider.FACEBOOK_API;
                    default:
                        return AuthProvider.EMAIL_API;
                }
            }
        }

        return AuthProvider.NONE;
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
