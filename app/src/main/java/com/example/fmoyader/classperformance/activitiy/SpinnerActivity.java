package com.example.fmoyader.classperformance.activitiy;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.utils.Spinnable;

/**
 * Created by fmoyader on 28/7/17.
 */

public class SpinnerActivity extends ShowErrorActivity implements Spinnable{

    private ViewGroup spinnerView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        configureSpinner();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        configureSpinner();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        configureSpinner();
    }

    private void configureSpinner() {
        ViewGroup view = (ViewGroup)getWindow().getDecorView();
        /* --- Relative Layout --- */
        // layout_width, layout_height
        RelativeLayout layout = new RelativeLayout(this);
        ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setLayoutParams(layoutParams);
        // visibility
        layout.setVisibility(View.INVISIBLE);
        // background_color
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.translucent));

        /* --- Progress Bar --- */
        // layout_width, layout_height
        ProgressBar progressBar = new ProgressBar(this);
        RelativeLayout.LayoutParams progressBarLayoutParams = new RelativeLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.progress_bar_width),
                (int) getResources().getDimension(R.dimen.progress_bar_height)
        );
        // center_in_parent
        progressBarLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(progressBarLayoutParams);

        /* --- View Hierarchy --- */
        layout.addView(progressBar);
        view.addView(layout);
        spinnerView = layout;
    }

    @Override
    public void onStartLoader() {
        spinnerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopLoader() {
        spinnerView.setVisibility(View.GONE);
    }
}
