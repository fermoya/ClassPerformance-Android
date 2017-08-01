package com.example.fmoyader.classperformance.presenter;

import android.content.Context;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.authentication.AuthManager;
import com.example.fmoyader.classperformance.firebase.FirebaseDBManager;
import com.example.fmoyader.classperformance.model.Course;
import com.example.fmoyader.classperformance.presenter.contract.AddNewCourseContract;
import com.example.fmoyader.classperformance.utils.TextUtils;

/**
 * Created by fmoyader on 31/7/17.
 */

public class AddNewCoursePresenter implements AddNewCourseContract.Presenter {

    private static final String COURSE_REFERENCE = "courses";

    private AddNewCourseContract.View addNewCourseView;
    private Context context;

    public AddNewCoursePresenter(AddNewCourseContract.View addNewCourseView, Context context) {
        this.addNewCourseView = addNewCourseView;
        this.context = context;
    }

    @Override
    public void onAddNewCourse() {
        String courseName = addNewCourseView.getCourseName();
        String courseDescription = addNewCourseView.getCourseDescripton();

        if (TextUtils.isEmpty(courseName, courseDescription)) {
            String errorMessage = context.getString(R.string.error_invalid_data);
            addNewCourseView.onShowError(errorMessage);
        } else {
            FirebaseDBManager manager = new FirebaseDBManager(COURSE_REFERENCE);

            String user = AuthManager.getaAuthenticatedUserId();
            if (TextUtils.isNotEmpty(user)) {
                manager.save(new Course(courseName, courseDescription, user));
                addNewCourseView.onFinish();
            }
        }
    }
}
