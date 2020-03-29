package com.application.ksgu.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.application.ksgu.Library.CustomSearchDialogCompat;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPemohonFragment extends Fragment implements BlockingStep {

    View view;
    EditText et_layanan,et_tanggal;
    int layanan_id;
    List<Layanan> layanans;
    SweetAlertDialog sweetAlertDialog;
    private int mYear,mMonth,mDay;
    private SimpleDateFormat dateFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_data_pemohon, container, false);
        et_layanan          = view.findViewById(R.id.et_layanan);

        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);

        dateFormatter       = new SimpleDateFormat("dd-MM-yyyy");

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
                                    layanan_id       = item.getJENISID();
                                    et_layanan.setText(item.getJENISNAME());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    getLayanan();
                }
            }
        });

        et_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_tanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
//        callback.getStepperLayout().showProgress("Operation in progress, please wait...");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                callback.goToNextStep();
//                callback.getStepperLayout().hideProgress();
//            }
//        }, 2000L);
        callback.goToNextStep();
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

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void getLayanan(){
        sweetAlertDialog.show();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Layanan>> call    = apiInterface.getLayanan();
        call.enqueue(new Callback<List<Layanan>>() {
            @Override
            public void onResponse(Call<List<Layanan>> call, Response<List<Layanan>> response) {
                sweetAlertDialog.hide();
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
                                    layanan_id       = item.getJENISID();
                                    et_layanan.setText(item.getJENISNAME());
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
                sweetAlertDialog.hide();
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
}
