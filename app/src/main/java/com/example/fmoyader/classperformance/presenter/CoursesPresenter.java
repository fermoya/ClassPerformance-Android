package com.example.fmoyader.classperformance.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.fmoyader.classperformance.activitiy.AddNewCourseActivity;
import com.example.fmoyader.classperformance.activitiy.CoursesActivity;
import com.example.fmoyader.classperformance.annotation.AuthProvider;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.authentication.AuthProcessListener;
import com.example.fmoyader.classperformance.authentication.impl.EmailAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.FacebookAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.GoogleAuthManager;
import com.example.fmoyader.classperformance.authentication.impl.TwitterAuthManager;
import com.example.fmoyader.classperformance.firebase.FirebaseDBManager;
import com.example.fmoyader.classperformance.firebase.FirebaseObservable;
import com.example.fmoyader.classperformance.firebase.FirebaseObserver;
import com.example.fmoyader.classperformance.model.Course;
import com.example.fmoyader.classperformance.presenter.contract.CoursesContract;
import com.example.fmoyader.classperformance.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmoyader on 26/7/17.
 */

public class CoursesPresenter implements CoursesContract.Presenter, AuthProcessListener, FirebaseObserver {

    private final Context context;
    private CoursesContract.View coursesView;
    private FirebaseDBManager firebaseManager;

    private static final String COURSE_REFERENCE = "courses";
    private static final String COURSE_USER_ID_REFERENCE = "user";

    public CoursesPresenter(CoursesContract.View coursesView, Context context) {
        this.coursesView = coursesView;
        this.context = context;
        firebaseManager = new FirebaseDBManager(COURSE_REFERENCE);
        firebaseManager.addObserver(this);
    }

    @Override
    public void onCreate() {
        String userId = AuthManager.getaAuthenticatedUserId();
        if (TextUtils.isNotEmpty(userId)) {
            firebaseManager.query(COURSE_USER_ID_REFERENCE, userId, Course.class);
        }
    }

    @Override
    public void onSingleTap() {
        startAddNewCourseActivity();
    }

    private void startAddNewCourseActivity() {
        Intent intentToAddNewActivity = new Intent(context, AddNewCourseActivity.class);
        coursesView.onStartActivity(intentToAddNewActivity);
    }

    @Override
    public void onAddNewCourse() {
        startAddNewCourseActivity();
    }

    @Override
    public void onBackPressed() {
        coursesView.onShowLogOutDialog();
    }

    @Override
    public void onLogOut() {
        AuthManager authManager;
        switch (AuthManager.getProvider()) {
            case AuthProvider.FACEBOOK_API:
                authManager = new FacebookAuthManager();
                break;
            case AuthProvider.TWITTER_API:
                authManager = new TwitterAuthManager();
                break;
            case AuthProvider.GOOGLE_API:
                authManager = new GoogleAuthManager(this, context, (CoursesActivity) coursesView);
                break;
            default:
                authManager = new EmailAuthManager();
                break;
        }

        authManager.logOut();
    }

    @Override
    public void onLogInSuccess() { }

    @Override
    public void onLogInError(String errorMessage) { }

    @Override
    public void onLogInCancel() { }

    @Override
    public void onResult(List<FirebaseObservable> results) {
        List<Course> courses = new ArrayList<>();
        for (FirebaseObservable result : results) {
            if (result != null && result instanceof Course) {
                courses.add((Course) result);
            }
        }
        coursesView.onRefreshList(courses);

        if (courses != null && !courses.isEmpty()) {
            coursesView.onShowCoursesList();
        } else {
            coursesView.onShowEmptyState();
        }
    }
}
