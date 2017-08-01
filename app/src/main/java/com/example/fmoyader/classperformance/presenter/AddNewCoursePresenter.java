package com.example.fmoyader.classperformance.presenter;

import com.example.fmoyader.classperformance.presenter.contract.AddNewCourseContract;
import com.example.fmoyader.classperformance.utils.TextUtils;

/**
 * Created by fmoyader on 31/7/17.
 */

public class AddNewCoursePresenter implements AddNewCourseContract.Presenter {


    private AddNewCourseContract.View addNewCourseView;

    public AddNewCoursePresenter(AddNewCourseContract.View addNewCourseView) {
        this.addNewCourseView = addNewCourseView;
    }

    @Override
    public void onAddNewCourse() {
        String courseName = addNewCourseView.getCourseName();
        String courseDescription = addNewCourseView.getCourseDescripton();

        if (TextUtils.isNotEmpty(courseName, courseDescription)) {
            addNewCourseView.onShowError();
        } else {
            addNewCourseView.onReturnResult();
        }
    }
}
