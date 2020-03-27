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

public class PermohonanActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    StepperLayout stepperLayout;
    StepperAdapter stepperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan);

        stepperLayout   = findViewById(R.id.stepperLayout);

        initTabLayout();
    }

    private void initTabLayout(){
        stepperAdapter = new StepperAdapter(getSupportFragmentManager(),this);

        stepperLayout.setAdapter(stepperAdapter);
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
}
