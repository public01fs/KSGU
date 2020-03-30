package com.application.ksgu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.application.ksgu.Adapter.PermohonanAdapter;
import com.application.ksgu.Adapter.StepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class PermohonanActivity extends AppCompatActivity implements StepperLayout.StepperListener,DataManager {

    StepperLayout stepperLayout;
    StepperAdapter stepperAdapter;
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final String DATA = "data";
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan);

        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        stepperLayout   = findViewById(R.id.stepperLayout);

        mData = savedInstanceState != null ? savedInstanceState.getString(DATA) : null;

        initTabLayout(startingStepPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.getCurrentStepPosition());
        outState.putString(DATA, mData);
        super.onSaveInstanceState(outState);
    }

    private void initTabLayout(int position){
        stepperAdapter = new StepperAdapter(getSupportFragmentManager(),this);

        stepperLayout.setAdapter(stepperAdapter,position);
        stepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }

    @Override
    public void saveData(String data) {
        mData = data;
    }

    public String getData() {
        return mData;
    }
}
