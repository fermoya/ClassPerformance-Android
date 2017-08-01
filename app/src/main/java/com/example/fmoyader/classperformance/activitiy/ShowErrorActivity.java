package com.example.fmoyader.classperformance.activitiy;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.fmoyader.classperformance.R;

/**
 * Created by fmoyader on 31/7/17.
 */

public class ShowErrorActivity extends AppCompatActivity {

    public void onShowError() {
        String errorMessage = getString(R.string.error_unexpected_error);
        onShowError(errorMessage);
    }

    public void onShowError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_dialog_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(R.string.alert_dialog_error_positive_button, null)
                .show();
    }
}
