package com.example.fmoyader.classperformance.presenters;

/**
 * Created by fmoyader on 26/7/17.
 */

public interface CoursesContract {
    interface Presenter {
        void onBackPressed();
        void onLogOut();
        void onCreate();
    }

    interface View {
        void onShowLogOutDialog();
    }
}
