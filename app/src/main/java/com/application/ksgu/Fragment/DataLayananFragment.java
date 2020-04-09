package com.application.ksgu.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.DataNota;
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

public class DataLayananFragment extends Fragment implements BlockingStep {

    public static DataLayananFragment newInstance() {
        return new DataLayananFragment();
    }

    private DataManager dataManager;
    SweetAlertDialog sweetAlertDialog;
    View view;
    DataKirim dataKirim;
    Gson gson = new Gson();
    RecyclerView rv_check;
    ItemCheckAdapter itemCheckAdapter;

    CardView cv_kapal,cv_layanan,cv_perusahaan,cv_laut;

    EditText et_pendaftaran,et_posisi,et_kapal,et_gt,et_callsign,et_bendera,et_pendaftaran1,et_pemilik;
    EditText et_perusahaan,et_alamatp,et_noidentitas,et_jmlkapal,et_totalgt;
    EditText et_kode,et_pelaut,et_tempat,et_tgllhr,et_umur,et_kelamin,et_status,et_sertifikat;

    Button btn_daftar,btn_daftarlaut;

    LinearLayout ll_data,ll_datalaut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view                = inflater.inflate(R.layout.fragment_data_layanan, container, false);
        rv_check            = view.findViewById(R.id.rv_check);
        cv_layanan          = view.findViewById(R.id.cv_layanan);
        cv_kapal            = view.findViewById(R.id.cv_kapal);
        et_pendaftaran      = view.findViewById(R.id.et_pendaftaran);
        et_posisi           = view.findViewById(R.id.et_posisi);
        et_kapal            = view.findViewById(R.id.et_kapal);
        et_gt               = view.findViewById(R.id.et_gt);
        et_callsign         = view.findViewById(R.id.et_callsign);
        et_bendera          = view.findViewById(R.id.et_bendera);
        et_pendaftaran1     = view.findViewById(R.id.et_pendaftaran1);
        et_pemilik          = view.findViewById(R.id.et_pemilik);
        btn_daftar          = view.findViewById(R.id.btn_daftar);
        ll_data             = view.findViewById(R.id.ll_data);
        cv_perusahaan       = view.findViewById(R.id.cv_perusahaan);
        et_perusahaan       = view.findViewById(R.id.et_perusahaan);
        et_alamatp          = view.findViewById(R.id.et_alamatp);
        et_noidentitas      = view.findViewById(R.id.et_noidentitas);
        et_jmlkapal         = view.findViewById(R.id.et_jmlkapal);
        et_totalgt          = view.findViewById(R.id.et_totalgt);
        cv_laut             = view.findViewById(R.id.cv_laut);
        et_kode             = view.findViewById(R.id.et_kode);
        et_pelaut           = view.findViewById(R.id.et_pelaut);
        et_tempat           = view.findViewById(R.id.et_tempat);
        et_tgllhr           = view.findViewById(R.id.et_tgllhr);
        et_umur             = view.findViewById(R.id.et_umur);
        et_kelamin          = view.findViewById(R.id.et_kelamin);
        et_status           = view.findViewById(R.id.et_status);
        et_sertifikat       = view.findViewById(R.id.et_sertifikat);
        btn_daftarlaut      = view.findViewById(R.id.btn_daftarlaut);
        ll_datalaut         = view.findViewById(R.id.ll_datalaut);

        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        callback.getStepperLayout().showProgress("Mohon Tunggu...");
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Checklist>> call  = apiInterface.getCheckList(dataKirim.getLayanan().getJENISID());
        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                callback.getStepperLayout().hideProgress();
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        dataKirim.setChecklists(response.body());
                    } else {
                        List<Checklist> checklists = new ArrayList<>();
                        dataKirim.setChecklists(checklists);
                    }
                    dataManager.saveData(gson.toJson(dataKirim));
                    callback.goToNextStep();
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                callback.getStepperLayout().hideProgress();
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

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
        dataKirim       = gson.fromJson(dataManager.getData(),DataKirim.class);
        if (dataKirim.getDataNotas().size() > 0){
            cv_layanan.setVisibility(View.VISIBLE);
            itemCheckAdapter        = new ItemCheckAdapter(dataKirim.getDataNotas());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
            rv_check.setLayoutManager(mLayoutManager);
            rv_check.setItemAnimator(new DefaultItemAnimator());
            rv_check.setAdapter(itemCheckAdapter);
        } else {
            cv_layanan.setVisibility(View.GONE);
        }

        if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("kapal")){
            updateKapal();
        } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("perusahaan")){
            updatePerusahaan();
        } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("pelaut")){
            updateLaut();
        }

    }

    @Override
    public void onError(@NonNull VerificationError error) {

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

    private void updateKapal(){
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_data.setVisibility(View.VISIBLE);
            }
        });

        cv_kapal.setVisibility(View.VISIBLE);
        cv_perusahaan.setVisibility(View.GONE);
        cv_laut.setVisibility(View.GONE);
        ll_datalaut.setVisibility(View.GONE);
    }

    private void updateLaut(){
        btn_daftarlaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_datalaut.setVisibility(View.VISIBLE);
            }
        });

        cv_laut.setVisibility(View.VISIBLE);
        cv_perusahaan.setVisibility(View.GONE);
        cv_kapal.setVisibility(View.GONE);
        ll_data.setVisibility(View.GONE);
    }

    private void updatePerusahaan(){
        cv_perusahaan.setVisibility(View.VISIBLE);
        cv_kapal.setVisibility(View.GONE);
        cv_laut.setVisibility(View.GONE);
        ll_data.setVisibility(View.GONE);
        ll_datalaut.setVisibility(View.GONE);
    }
}
