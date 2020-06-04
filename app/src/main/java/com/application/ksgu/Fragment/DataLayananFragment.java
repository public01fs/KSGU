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

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.ksgu.Adapter.DitkapelAdapter;
import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.Adapter.KapalAdapter;
import com.application.ksgu.Adapter.NegaraAdapter;
import com.application.ksgu.Adapter.PelautAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.Model.Ditkapel;
import com.application.ksgu.Model.DitkapelService;
import com.application.ksgu.Model.Kapal;
import com.application.ksgu.Model.Negara;
import com.application.ksgu.Model.Pelaut;
import com.application.ksgu.Model.PelautService;
import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_TOKEN;

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

    EditText et_posisi,et_gt,et_callsign,et_pendaftaran1,et_pemilik;
    EditText et_perusahaan,et_alamatp,et_noidentitas,et_jmlkapal,et_totalgt;
    EditText et_pelaut,et_tempat,et_tgllhr,et_umur,et_kelamin,et_status,et_sertifikat;

    AutoCompleteTextView et_pendaftaran,et_kode,et_kapal,et_bendera;

    Button btn_daftar,btn_daftarlaut;

    LinearLayout ll_data,ll_datalaut;

    SessionManager sessionManager;
    HashMap<String, String> getLogin;

    DitkapelAdapter ditkapelAdapter;
    PelautAdapter pelautAdapter;
    NegaraAdapter negaraAdapter;
    KapalAdapter kapalAdapter;
    List<Ditkapel>ditkapels;
    List<Pelaut> pelauts;
    List<Negara> negaras;
    List<Kapal> kapals;
    List<DataNota> dataCheck;

    int kapal_id;
    String foto_pelaut;

    PelautService pelautService;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 100;

    private Handler handler,handler1,handler2;

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
        sessionManager      = new SessionManager(getContext());
        getLogin            = sessionManager.getLogin();

        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

//        getDitkapel("a");

        ditkapelAdapter = new DitkapelAdapter(getActivity());
        et_pendaftaran.setThreshold(1);
        et_pendaftaran.setAdapter(ditkapelAdapter);

        et_pendaftaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_pendaftaran.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getDitkapelService(ditkapelAdapter.getItem(i).getValue());
            }
        });

        pelautAdapter = new PelautAdapter(getActivity());
        et_kode.setThreshold(1);
        et_kode.setAdapter(pelautAdapter);

        et_kode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_kode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getPelautService(pelautAdapter.getItem(i).getValue());
            }
        });

        negaraAdapter = new NegaraAdapter(getActivity());
        et_bendera.setThreshold(1);
        et_bendera.setAdapter(negaraAdapter);

        et_bendera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler1.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler1.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        kapalAdapter = new KapalAdapter(getActivity());
        et_kapal.setThreshold(1);
        et_kapal.setAdapter(kapalAdapter);

        et_kapal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler2.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler2.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_kapal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                kapal_id = kapalAdapter.getItem(i).getId();
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("kapal")){
                        if (!TextUtils.isEmpty(et_pendaftaran.getText())) {
                            getDitkapel(et_pendaftaran.getText().toString());
                        }
                    } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("pelaut")){
                        if (!TextUtils.isEmpty(et_kode.getText())) {
                            getPelaut(et_kode.getText().toString());
                        }
                    }
                }
                return false;
            }
        });

        handler1 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(et_bendera.getText())) {
                        getNegara(et_bendera.getText().toString());
                    }
                }
                return false;
            }
        });

        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(et_kapal.getText())) {
                        getKapal(et_kapal.getText().toString());
                    }
                }
                return false;
            }
        });


        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        callback.getStepperLayout().showProgress("Mohon Tunggu...");
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
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

                    if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("kapal")){
                        setDataKapal();
                    } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("perusahaan")){
                        setDataPerusahaan();
                    } else if (dataKirim.getLayanan().getREQUIRECHECK().toLowerCase().equals("pelaut")){
                        setDataPelaut();
                    }

                    if (itemCheckAdapter != null){
                        dataCheck   = itemCheckAdapter.getCheck();
                    }

                    if (dataCheck != null && dataCheck.size() > 0){
                        dataKirim.setDataCheck(dataCheck);
                    } else {
                        dataKirim.setDataCheck(dataCheck);
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

    public void getDitkapel(String term){
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Ditkapel>> call       = apiInterface.getDitkapel(term);
        call.enqueue(new Callback<List<Ditkapel>>() {
            @Override
            public void onResponse(Call<List<Ditkapel>> call, Response<List<Ditkapel>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        ditkapels = response.body();
                        ditkapelAdapter.setData(ditkapels);
                        ditkapelAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ditkapel>> call, Throwable t) {

            }
        });
    }

    public void getDitkapelService(String pendaftaran){
        showpDialog();
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<DitkapelService>> call      = apiInterface.getDitkapelService(pendaftaran);
        call.enqueue(new Callback<List<DitkapelService>>() {
            @Override
            public void onResponse(Call<List<DitkapelService>> call, Response<List<DitkapelService>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    ll_data.setVisibility(View.VISIBLE);
                    List<DitkapelService> ditkapelServices = response.body();
                    if (ditkapelServices.size() > 0){
                        et_kapal.setText(ditkapelServices.get(0).getNAMAKPL());
                        et_gt.setText(ditkapelServices.get(0).getISIKOTOR());
                        et_bendera.setText(ditkapelServices.get(0).getBenderaAsal());
                        et_pendaftaran1.setText(ditkapelServices.get(0).getTANDADAFTAR());
                        et_callsign.setText(ditkapelServices.get(0).getCALLSIGN());
                        et_pemilik.setText(ditkapelServices.get(0).getNAMAPEMILIK());
                        et_posisi.setText(ditkapelServices.get(0).getKAPALPOSISI());
                        kapal_id    = ditkapelServices.get(0).getIDKAPAL();
                    }
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DitkapelService>> call, Throwable t) {
                hidepDialog();
                Log.e("error",t.getMessage());
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPelaut(String term){
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Pelaut>> call         = apiInterface.getPelaut(term);
        call.enqueue(new Callback<List<Pelaut>>() {
            @Override
            public void onResponse(Call<List<Pelaut>> call, Response<List<Pelaut>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        pelauts = response.body();
                        pelautAdapter.setData(pelauts);
                        pelautAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pelaut>> call, Throwable t) {

            }
        });
    }

    public void getPelautService(String kode){
        showpDialog();
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<PelautService> call        = apiInterface.getPelautService(kode);
        call.enqueue(new Callback<PelautService>() {
            @Override
            public void onResponse(Call<PelautService> call, Response<PelautService> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    ll_datalaut.setVisibility(View.VISIBLE);
                    pelautService = response.body();
                    et_pelaut.setText(pelautService.getNamaPelaut());
                    et_tempat.setText(pelautService.getTempatLahir());
                    et_tgllhr.setText(pelautService.getTglLahir());
                    et_umur.setText(pelautService.getUmur());
                    et_kelamin.setText(pelautService.getJK());
                    et_status.setText(pelautService.getStatusPelaut());
                    et_sertifikat.setText(pelautService.getSertifikat());
                    foto_pelaut = pelautService.getFotoPelaut();
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PelautService> call, Throwable t) {
                hidepDialog();
                Log.e("error",t.getMessage());
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNegara(String term){
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Negara>> call         = apiInterface.getNegara(term);
        call.enqueue(new Callback<List<Negara>>() {
            @Override
            public void onResponse(Call<List<Negara>> call, Response<List<Negara>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        negaras = response.body();
                        negaraAdapter.setData(negaras);
                        negaraAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Negara>> call, Throwable t) {

            }
        });
    }

    public void getKapal(String term){
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Kapal>> call          = apiInterface.getKapal(term);
        call.enqueue(new Callback<List<Kapal>>() {
            @Override
            public void onResponse(Call<List<Kapal>> call, Response<List<Kapal>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        kapals = response.body();
                        kapalAdapter.setData(kapals);
                        kapalAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Kapal>> call, Throwable t) {

            }
        });
    }

    private void setDataKapal(){
        dataKirim.setKapal_name(et_kapal.getText().toString());
        dataKirim.setKapal_gt(et_gt.getText().toString());
        dataKirim.setKapal_cs(et_callsign.getText().toString());
        dataKirim.setKapal_bendera(et_bendera.getText().toString());
        dataKirim.setKapal_pemilik(et_pemilik.getText().toString());
        dataKirim.setKapal_posisi(et_posisi.getText().toString());
        dataKirim.setKapal_id((TextUtils.isEmpty(String.valueOf(kapal_id)))?"":String.valueOf(kapal_id));
        dataKirim.setNamaprsh("");
        dataKirim.setAlamatprsh("");
        dataKirim.setIdentitasprsh("");
        dataKirim.setJmlkapal("");
        dataKirim.setTotalgt("");
        dataKirim.setIdpelaut("");
        dataKirim.setNamapelaut("");
        dataKirim.setKodepelaut("");
        dataKirim.setTempatlahir("");
        dataKirim.setTgllahir("");
        dataKirim.setUmur("");
        dataKirim.setJk("");
        dataKirim.setStatuspelaut("");
        dataKirim.setSertifikat("");
        dataKirim.setFotopelaut("");
    }

    private void setDataPerusahaan(){
        dataKirim.setKapal_name("");
        dataKirim.setKapal_gt("");
        dataKirim.setKapal_cs("");
        dataKirim.setKapal_bendera("");
        dataKirim.setKapal_pemilik("");
        dataKirim.setKapal_posisi("");
        dataKirim.setKapal_id("");
        dataKirim.setNamaprsh(et_perusahaan.getText().toString());
        dataKirim.setAlamatprsh(et_alamatp.getText().toString());
        dataKirim.setIdentitasprsh(et_noidentitas.getText().toString());
        dataKirim.setJmlkapal(et_jmlkapal.getText().toString());
        dataKirim.setTotalgt(et_totalgt.getText().toString());
        dataKirim.setIdpelaut("");
        dataKirim.setNamapelaut("");
        dataKirim.setKodepelaut("");
        dataKirim.setTempatlahir("");
        dataKirim.setTgllahir("");
        dataKirim.setUmur("");
        dataKirim.setJk("");
        dataKirim.setStatuspelaut("");
        dataKirim.setSertifikat("");
        dataKirim.setFotopelaut("");
    }

    private void setDataPelaut(){
        dataKirim.setKapal_name("");
        dataKirim.setKapal_gt("");
        dataKirim.setKapal_cs("");
        dataKirim.setKapal_bendera("");
        dataKirim.setKapal_pemilik("");
        dataKirim.setKapal_posisi("");
        dataKirim.setKapal_id("");
        dataKirim.setNamaprsh("");
        dataKirim.setAlamatprsh("");
        dataKirim.setIdentitasprsh("");
        dataKirim.setJmlkapal("");
        dataKirim.setTotalgt("");
        if (pelautService == null){
            dataKirim.setIdpelaut("");
            dataKirim.setKodepelaut("");
        } else {
            dataKirim.setIdpelaut(String.valueOf(pelautService.getIdPelaut()));
            dataKirim.setKodepelaut(String.valueOf(pelautService.getKodePelaut()));
        }
        dataKirim.setNamapelaut(et_pelaut.getText().toString());
        dataKirim.setTempatlahir(et_tempat.getText().toString());
        dataKirim.setTgllahir(et_tgllhr.getText().toString());
        dataKirim.setUmur(et_umur.getText().toString());
        dataKirim.setJk(et_kelamin.getText().toString());
        dataKirim.setStatuspelaut(et_status.getText().toString());
        dataKirim.setSertifikat(et_sertifikat.getText().toString());
        if (foto_pelaut == null || foto_pelaut.equals("")){
            dataKirim.setFotopelaut("");
        } else {
            dataKirim.setFotopelaut(foto_pelaut);
        }
    }

    private void showpDialog() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }

    private void hidepDialog() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }
}
