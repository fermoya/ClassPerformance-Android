package com.example.fmoyader.classperformance.activitiy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.adapter.CoursesRecyclerViewAdapter;
import com.example.fmoyader.classperformance.model.Course;
import com.example.fmoyader.classperformance.presenter.contract.CoursesContract;
import com.example.fmoyader.classperformance.presenter.CoursesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesActivity extends AppCompatActivity implements CoursesContract.View {

    public static final String COURSE_NAME_EXTRA = "course name";
    public static final String COURSE_DESCRIPTION_EXTRA = "course description";

    private CoursesContract.Presenter presenter;

    @BindView(R.id.text_view_empty_courses) TextView emptyCoursesTextView;
    @BindView(R.id.recycler_view_courses) RecyclerView coursesRecylyerView;
    private GestureDetector tapGestureDetector;
    private CoursesRecyclerViewAdapter coursesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle(getString(R.string.activity_courses_title));

        tapGestureDetector = new GestureDetector(this, new TapGestureListener());

        emptyCoursesTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        coursesAdapter = new CoursesRecyclerViewAdapter(this);
        coursesRecylyerView.setAdapter(coursesAdapter);
        coursesRecylyerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

        presenter = new CoursesPresenter(this, this);
        presenter.onCreate();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    public void addNewCourse(View view) {
        presenter.onAddNewCourse();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            presenter.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void onShowLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_dialog_logout_title))
                .setMessage(getString(R.string.alert_dialog_logout_message))
                .setPositiveButton(R.string.alert_dialog_logout_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onLogOut();
                        // CoursesActivity.this.finish();
                        Intent intentToLogInActivity = new Intent(CoursesActivity.this, LogInActivity.class);
                        startActivity(intentToLogInActivity);
                    }
                })
                .setNegativeButton(R.string.alert_dialog_logout_negative_button, null)
                .show();
    }

    @Override
    public void onStartActivity(Intent intentToAddNewActivity) {
        startActivity(intentToAddNewActivity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefreshList(List<Course> courses) {
        coursesAdapter.refreshWithNewList(courses);
    }

    @Override
    public void onShowCoursesList() {
        coursesRecylyerView.setVisibility(View.VISIBLE);
        emptyCoursesTextView.setVisibility(View.GONE);
    }

    @Override
    public void onShowEmptyState() {
        emptyCoursesTextView.setVisibility(View.VISIBLE);
        coursesRecylyerView.setVisibility(View.GONE);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            presenter.onSingleTap();
            return super.onSingleTapConfirmed(e);
        }
    }
}
