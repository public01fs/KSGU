package com.application.ksgu.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.Library.CustomSearchDialogCompat;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.OnNavigationBarListener;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;
import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.application.ksgu.Cons.KEY_AGEN_ID;
import static com.application.ksgu.Cons.KEY_AGEN_NAMA;
import static com.application.ksgu.Cons.KEY_ALAMAT;
import static com.application.ksgu.Cons.KEY_NAME;
import static com.application.ksgu.Cons.KEY_NPWP;
import static com.application.ksgu.Cons.KEY_TELEPON;
import static com.application.ksgu.Cons.KEY_TOKEN;

public class DataPemohonFragment extends Fragment implements BlockingStep {

    public static DataPemohonFragment newInstance() {
        return new DataPemohonFragment();
    }

    View view;
    EditText et_layanan,et_tanggal,et_nomor,et_perihal;
    EditText et_nama,et_npwp,et_alamat,et_pic,et_telepon;
    int layanan_id;
    List<Layanan> layanans;
    SweetAlertDialog sweetAlertDialog;
    private int mYear,mMonth,mDay;
    private SimpleDateFormat dateFormatter;
    DataKirim dataKirim = new DataKirim();
    private DataManager dataManager;
    Layanan layanan;
    Gson gson = new Gson();
    Boolean check = false;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    SharedPreferences prefs;
    String layanan_kode;
    int agen_id;

    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_data_pemohon, container, false);
        et_layanan          = view.findViewById(R.id.et_layanan);
        et_tanggal          = view.findViewById(R.id.et_tanggal);
        et_nama             = view.findViewById(R.id.et_nama);
        et_npwp             = view.findViewById(R.id.et_npwp);
        et_alamat           = view.findViewById(R.id.et_alamat);
        et_pic              = view.findViewById(R.id.et_pic);
        et_telepon          = view.findViewById(R.id.et_telepon);
        et_nomor            = view.findViewById(R.id.et_nomor);
        et_perihal          = view.findViewById(R.id.et_perihal);
        prefs               = getActivity().getSharedPreferences("layanan",Context.MODE_PRIVATE);
        layanan_kode        = prefs.getString("data","");
        sessionManager      = new SessionManager(getContext());

        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        dateFormatter       = new SimpleDateFormat("dd-MM-yyyy");

        if (savedInstanceState != null) {
            check = savedInstanceState.getBoolean("check");
        }

        updateNavigationBar(check);

        et_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layanans != null) {
                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(getContext(), "Layanan",
                            "Pilih jenis permohonan penerbitan", null, (ArrayList) layanans,
                            new SearchResultListener<Layanan>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        Layanan item, int position1
                                ) {
                                    layanan = item;
                                    et_layanan.setText(item.getJENISNAME());
                                    check   = true;
                                    updateNavigationBar(check);
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    getLayanan(layanan_kode);
                }
            }
        });

        et_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        callback.getStepperLayout().showProgress("Mohon Tunggu...");
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<DataNota>> call       = apiInterface.getDataNota(layanan.getJENISID());
        call.enqueue(new Callback<List<DataNota>>() {
            @Override
            public void onResponse(Call<List<DataNota>> call, Response<List<DataNota>> response) {
                callback.getStepperLayout().hideProgress();
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        dataKirim.setDataNotas(response.body());
                    } else {
                        List<DataNota> dataNotas = new ArrayList<>();
                        dataKirim.setDataNotas(dataNotas);
                    }
                    dataKirim.setLayanan(layanan);
                    dataKirim.setJenis_id(String.valueOf(layanan.getJENISID()));
                    dataKirim.setRequire_check(layanan.getREQUIRECHECK());
                    dataKirim.setIdkantor(String.valueOf(agen_id));
                    dataKirim.setSurat_hal(et_perihal.getText().toString());
                    dataKirim.setSurat_no(et_nomor.getText().toString());
                    dataKirim.setSurat_tgl(et_nomor.getText().toString());
                    dataKirim.setSurat_npwp(et_npwp.getText().toString());
                    dataKirim.setSurat_pengirim(et_nama.getText().toString());
                    dataKirim.setSurat_pengirim_kota(et_alamat.getText().toString());
                    dataManager.saveData(gson.toJson(dataKirim));
                    callback.goToNextStep();
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataNota>> call, Throwable t) {
                callback.getStepperLayout().hideProgress();
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
//        callback.goToNextStep();
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
        if (layanan == null){
            return new VerificationError("Pilih layanan terlebih dahulu");
        } else {
            return null;
        }
    }

    @Override
    public void onSelected() {
        setLayout();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void getLayanan(String layanan_kode){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Layanan>> call    = apiInterface.getLayanan(layanan_kode);
        call.enqueue(new Callback<List<Layanan>>() {
            @Override
            public void onResponse(Call<List<Layanan>> call, Response<List<Layanan>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    layanans    = response.body();

                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(getContext(), "Layanan",
                            "Pilih jenis permohonan penerbitan", null, (ArrayList) layanans,
                            new SearchResultListener<Layanan>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        Layanan item, int position1
                                ) {
                                    layanan = item;
                                    et_layanan.setText(item.getJENISNAME());
                                    check   = true;
                                    updateNavigationBar(check);
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);

                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Layanan>> call, Throwable t) {
                hidepDialog();
                Log.d("error",t.toString());
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        if (mYear == 0 || mMonth == 0 || mDay == 0) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(selectedYear, selectedMonth, selectedDay);
                et_tanggal.setText(dateFormatter.format(newDate.getTime()));
                mYear = selectedYear;
                mMonth = selectedMonth;
                mDay = selectedDay;
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), datePickerListener, mYear, mMonth, mDay);

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                        }
                    }
                });

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManager) {
            dataManager = (DataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }

        if (context instanceof OnNavigationBarListener) {
            onNavigationBarListener = (OnNavigationBarListener) context;
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

    private void updateNavigationBar(Boolean check) {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(check);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("check", check);
        super.onSaveInstanceState(outState);
    }

    private void setLayout(){
        getLogin    = sessionManager.getLogin();
        agen_id     = Integer.valueOf(getLogin.get(KEY_AGEN_ID));
        et_alamat.setText(getLogin.get(KEY_ALAMAT));
        et_pic.setText(getLogin.get(KEY_NAME));
        et_npwp.setText(getLogin.get(KEY_NPWP));
        et_telepon.setText(getLogin.get(KEY_TELEPON));
        et_nama.setText(getLogin.get(KEY_AGEN_NAMA));
    }
}
