package com.example.fmoyader.classperformance.presenter.contract;

import android.content.Intent;

import com.example.fmoyader.classperformance.firebase.FirebaseObservable;
import com.example.fmoyader.classperformance.model.Course;

import java.util.List;

/**
 * Created by fmoyader on 26/7/17.
 */

public interface CoursesContract {
    interface Presenter {
        void onBackPressed();
        void onLogOut();
        void onCreate();
        void onSingleTap();
        void onAddNewCourse();
    }

    interface View {
        void onShowLogOutDialog();
        void onStartActivity(Intent intentToAddNewActivity);
        void onRefreshList(List<Course> results);
        void onShowEmptyState();
        void onShowCoursesList();
    }
}
