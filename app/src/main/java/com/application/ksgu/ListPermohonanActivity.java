package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.SuratListAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Document;
import com.application.ksgu.Model.Surat;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_TOKEN;

public class ListPermohonanActivity extends AppCompatActivity {

    RecyclerView rv_permohonan;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    SuratListAdapter suratListAdapter;
    SweetAlertDialog sweetAlertDialog;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    LinearLayout ll_notfound;
    TextView tv_title;
    SearchView searchView;
    ImageView iv_back;
    List<Surat> surats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_permohonan);

        rv_permohonan       = findViewById(R.id.rv_permohonan);
        ll_notfound         = findViewById(R.id.ll_notfound);
        tv_title            = findViewById(R.id.tv_title);
        searchView          = findViewById(R.id.search);
        iv_back             = findViewById(R.id.iv_back);
        sessionManager      = new SessionManager(this);
        getLogin            = sessionManager.getLogin();
        prefs               = getSharedPreferences("detail", Context.MODE_PRIVATE);
        editor              = prefs.edit();
        sweetAlertDialog    = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        searchView.setMaxWidth(Integer.MAX_VALUE);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                return false;
            }
        });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                beginSearch(newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.

                // Log.e("Query",query);


                return false;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        if (getIntent().getExtras() != null){
            tv_title.setText(getIntent().getStringExtra("judul"));
            getSurat(getIntent().getStringExtra("id"));
        }

    }

    private void getSurat(String keterangan){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Surat>> call      = null;
        if (keterangan.equals("baru")){
            call                    = apiInterface.getSuratNew();
        } else if (keterangan.equals("proses")){
            call                    = apiInterface.getSuratProses();
        } else {
            call                    = apiInterface.getSuratFinish();
        }
        call.enqueue(new Callback<List<Surat>>() {
            @Override
            public void onResponse(Call<List<Surat>> call, Response<List<Surat>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    surats          = response.body();
                    rv_permohonan.setLayoutManager(new LinearLayoutManager(ListPermohonanActivity.this));
                    rv_permohonan.setHasFixedSize(true);

                    //set data and list adapter
                    suratListAdapter = new SuratListAdapter(ListPermohonanActivity.this, response.body());
                    rv_permohonan.setAdapter(suratListAdapter);

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
                    Toast.makeText(ListPermohonanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Surat>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(ListPermohonanActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(ListPermohonanActivity.this, PermohonanDetailActivity.class));
                } else {
                    Toast.makeText(ListPermohonanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSuratNew> call, Throwable t) {
                hidepDialog();
                Toast.makeText(ListPermohonanActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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

    public void beginSearch(String query) {
        suratListAdapter.changeDataSet(searchData(query));
    }

    private List<Surat> searchData(String query) {
        List<Surat> data = new ArrayList<>();
        for (Surat j : surats) {
            String permohonan   = j.getJENISNAME().toLowerCase();
            if (permohonan.contains(query.toLowerCase())) {
                data.add(j);
            }
        }

        return data;
    }
}
