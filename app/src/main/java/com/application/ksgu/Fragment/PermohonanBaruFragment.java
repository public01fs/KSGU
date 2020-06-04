package com.application.ksgu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.application.ksgu.Adapter.SuratListAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Surat;
import com.application.ksgu.PermohonanDetailActivity;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_TOKEN;

public class PermohonanBaruFragment extends Fragment {

    View view;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    SuratListAdapter suratListAdapter;
    SweetAlertDialog sweetAlertDialog;
    RecyclerView rv_data;
    LinearLayout ll_notfound;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_permohonan_baru, container, false);
        sessionManager      = new SessionManager(getContext());
        getLogin            = sessionManager.getLogin();
        rv_data             = view.findViewById(R.id.rv_data);
        ll_notfound         = view.findViewById(R.id.ll_notfound);
        prefs               = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        editor              = prefs.edit();
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        getSurat();

        return view;
    }

    private void getSurat(){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Surat>> call      = apiInterface.getSuratNew();
        call.enqueue(new Callback<List<Surat>>() {
            @Override
            public void onResponse(Call<List<Surat>> call, Response<List<Surat>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    rv_data.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_data.setHasFixedSize(true);

                    //set data and list adapter
                    suratListAdapter = new SuratListAdapter(getActivity(), response.body());
                    rv_data.setAdapter(suratListAdapter);

                    suratListAdapter.setOnItemClickListener(new SuratListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, Surat obj, int position) {
                            getDetailSurat(obj.getSURATID());
                        }
                    });

                    if (response.body().size() > 0){
                        ll_notfound.setVisibility(View.GONE);
                    } else {
                        ll_notfound.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Surat>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailSurat(String nomor){
        showpDialog();
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<DetailSuratNew> call       = apiInterface.detailSurat(nomor);
        call.enqueue(new Callback<DetailSuratNew>() {
            @Override
            public void onResponse(Call<DetailSuratNew> call, Response<DetailSuratNew> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    editor.putString("detail",gson.toJson(response.body()));
                    editor.apply();
                    startActivity(new Intent(getContext(), PermohonanDetailActivity.class));
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSuratNew> call, Throwable t) {
                hidepDialog();
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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
