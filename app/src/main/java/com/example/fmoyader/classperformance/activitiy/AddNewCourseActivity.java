package com.example.fmoyader.classperformance.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenter.AddNewCoursePresenter;
import com.example.fmoyader.classperformance.presenter.contract.AddNewCourseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewCourseActivity extends ShowErrorActivity implements AddNewCourseContract.View{

    @BindView(R.id.edit_text_course_name) EditText courseNameEditText;
    @BindView(R.id.edit_text_course_description) EditText courseDescriptionEditText;

    private AddNewCourseContract.Presenter presenter;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        data = getIntent();

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AddNewCoursePresenter(this);
    }

    public void addNewCourse(View view) {
        presenter.onAddNewCourse();
    }

    @Override
    public String getCourseName() {
        return courseNameEditText.getText().toString();
    }

    @Override
    public String getCourseDescripton() {
        return courseDescriptionEditText.getText().toString();
    }

    @Override
    public void onReturnResult() {
        if (data != null) {
            data.putExtra(CoursesActivity.COURSE_NAME_EXTRA, getCourseName());
            data.putExtra(CoursesActivity.COURSE_DESCRIPTION_EXTRA, getCourseDescripton());
            setResult(Activity.RESULT_OK, data);
        }
    }
}
