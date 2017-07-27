package com.example.fmoyader.classperformance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fmoyader.classperformance.R;
import com.example.fmoyader.classperformance.presenters.SignUpContract;
import com.example.fmoyader.classperformance.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private SignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter = new SignUpPresenter(this);
    }

    public void onSignUp (View view) {
        presenter.onSignUp();
    }

    @Override
    public void checkData() {
        
    }
}
