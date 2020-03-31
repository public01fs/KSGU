package com.application.ksgu.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

public class UploadDokumenFragment extends Fragment implements BlockingStep {

    public static UploadDokumenFragment newInstance() {
        return new UploadDokumenFragment();
    }

    private DataManager dataManager;
    View view;
    RecyclerView rv_upload;
    Gson gson = new Gson();
    DataKirim dataKirim;
    UploadFotoAdapter uploadFotoAdapter;
    List<UploadFotoModel> menuList = new ArrayList<>();
    List<Checklist> checklists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_upload_dokumen, container, false);
        rv_upload       = view.findViewById(R.id.rv_upload);
        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
//        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        dataKirim       = gson.fromJson(dataManager.getData(), DataKirim.class);
        setRecycleView(dataKirim.getLayanan().getJENISID());

//        if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("kapal")){
//            updateKapal(view);
//        } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("perusahaan")){
//            updatePerusahaan(view);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManager) {
            dataManager = (DataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void setRecycleView(int id){
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Checklist>> call  = apiInterface.getCheckList(id);
        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        checklists  = response.body();

                        for (int i = 0; i < checklists.size(); i++) {
                            menuList.add(new UploadFotoModel(checklists.get(i).getCEKLISTNAME()));
                        }

                        uploadFotoAdapter = new UploadFotoAdapter(menuList);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
//                        rv_upload.addItemDecoration(new GridSpacingItemDecoration(2, tools.dpToPx(8), true));
                        rv_upload.setLayoutManager(mLayoutManager);
                        rv_upload.setItemAnimator(new DefaultItemAnimator());
                        rv_upload.setAdapter(uploadFotoAdapter);
                    } else {
//                        cv_layanan.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {

            }
        });
    }
}
