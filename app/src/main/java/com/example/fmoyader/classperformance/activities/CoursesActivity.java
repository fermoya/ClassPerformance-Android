package com.example.fmoyader.classperformance.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenters.CoursesContract;
import com.example.fmoyader.classperformance.presenters.CoursesPresenter;

public class CoursesActivity extends AppCompatActivity implements CoursesContract.View {

    private CoursesContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle(getString(R.string.activity_courses_title));

        presenter = new CoursesPresenter(this, this);
        presenter.onCreate();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
