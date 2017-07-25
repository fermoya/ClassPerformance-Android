package com.example.fmoyader.classperformance.presenters;

/**
 * Created by fmoyader on 25/7/17.
 */

public interface LogInContract {
    interface Presenter {
        void logInFacebook();
        void logInTwitter();
        void logInGoogle();
    }

    interface View {

    }
}
