package com.example.fmoyader.classperformance.presenter.contract;

import com.example.fmoyader.classperformance.utils.ErrorAlertDisplay;

/**
 * Created by fmoyader on 31/7/17.
 */

public interface AddNewCourseContract {
    interface Presenter {
        void onAddNewCourse();
    }

    interface View extends ErrorAlertDisplay{
        String getCourseName();
        String getCourseDescripton();
        void onFinish();
    }
}
