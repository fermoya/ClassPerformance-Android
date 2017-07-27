package com.example.fmoyader.classperformance.presenters;

/**
 * Created by fmoyader on 27/7/17.
 */

public interface SignUpContract {
    interface Presenter {
        void onSignUp();
    }

    interface View {
        void checkData();
    }
}
