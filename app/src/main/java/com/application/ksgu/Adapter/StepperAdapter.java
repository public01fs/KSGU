package com.application.ksgu.Adapter;

import android.content.Context;

import com.application.ksgu.Fragment.DataLayananFragment;
import com.application.ksgu.Fragment.DataPemohonFragment;
import com.application.ksgu.Fragment.UploadDokumenFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";
    public int temp;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Step step = null;
        switch (position){
            case 0:
                step = new DataPemohonFragment();
                break;
            case 1:
                step = new DataLayananFragment();
                break;
            case 2:
                step = new UploadDokumenFragment();
                break;
        }
        return step;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);

        switch (position){
            case 0:
                builder
                        .setTitle("Data")
                        .setEndButtonLabel("Berikutnya")
                .setSubtitle("Pemohon");
                break;
            case 1:
                builder
                        .setTitle("Data")
                        .setEndButtonLabel("Berikutnya")
                        .setBackButtonLabel("Sebelumnya")
                .setSubtitle("Layanan");
                break;
            case 2:
                builder
                        .setTitle("Upload")
                        .setEndButtonLabel("Selesai")
                        .setBackButtonLabel("Sebelumnya")
                .setSubtitle("Dokumen");
//            default:
//                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();
    }
}
