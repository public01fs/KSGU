package com.application.ksgu.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view                = inflater.inflate(R.layout.fragment_data_layanan, container, false);
        rv_check            = view.findViewById(R.id.rv_check);
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
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
        dataKirim       = gson.fromJson(dataManager.getData(),DataKirim.class);
        getCheck(dataKirim.getLayanan().getJENISID());
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

    private void getCheck(int id){
        showpDialog();
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<DataNota>> call = apiInterface.getDataNota(id);
        call.enqueue(new Callback<List<DataNota>>() {
            @Override
            public void onResponse(Call<List<DataNota>> call, Response<List<DataNota>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        itemCheckAdapter        = new ItemCheckAdapter(response.body());
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
//                        rv_check.addItemDecoration(new GridSpacingItemDecoration(1, tools.dpToPx(0), true));
                        rv_check.setLayoutManager(mLayoutManager);
                        rv_check.setItemAnimator(new DefaultItemAnimator());
                        rv_check.setAdapter(itemCheckAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataNota>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

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
