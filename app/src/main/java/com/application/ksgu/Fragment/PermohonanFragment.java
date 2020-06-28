package com.application.ksgu.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import it.sephiroth.android.library.easing.Linear;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.ListPermohonanActivity;
import com.application.ksgu.Model.Count;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;

import java.util.HashMap;

import static com.application.ksgu.Cons.KEY_TOKEN;

public class PermohonanFragment extends Fragment {

    View view;
    LinearLayout ll_baru,ll_proses,ll_selesai;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    SweetAlertDialog sweetAlertDialog;
    TextView tv_baru,tv_proses,tv_selesai,tv_ulasan;
    TextView tv_pemberitahuan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_permohonan, container, false);
        ll_baru             = view.findViewById(R.id.ll_baru);
        ll_proses           = view.findViewById(R.id.ll_proses);
        ll_selesai          = view.findViewById(R.id.ll_selesai);
        tv_baru             = view.findViewById(R.id.tv_request);
        tv_proses           = view.findViewById(R.id.tv_process);
        tv_selesai          = view.findViewById(R.id.tv_selesai);
        tv_ulasan           = view.findViewById(R.id.tv_ulasan);
        tv_pemberitahuan    = view.findViewById(R.id.tv_pemberitahuan);
        sessionManager      = new SessionManager(getContext());
        getLogin            = sessionManager.getLogin();
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);
        tv_pemberitahuan.setTypeface(ResourcesCompat.getFont(getContext(), R.font.poppins_bold));

        getCount();

        ll_baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i    = new Intent(getContext(), ListPermohonanActivity.class);
                i.putExtra("judul","Permohonan Baru");
                i.putExtra("id","baru");
                startActivity(i);
            }
        });

        ll_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i    = new Intent(getContext(), ListPermohonanActivity.class);
                i.putExtra("judul","Permohonan Proses");
                i.putExtra("id","proses");
                startActivity(i);
            }
        });

        ll_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i    = new Intent(getContext(), ListPermohonanActivity.class);
                i.putExtra("judul","Permohonan Selesai");
                i.putExtra("id","selesai");
                startActivity(i);
            }
        });

        return view;
    }

    private void getCount(){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<Count> call            = apiInterface.getCountPermohonan();
        call.enqueue(new Callback<Count>() {
            @Override
            public void onResponse(Call<Count> call, Response<Count> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    Count count     = response.body();

                    if (count.getRequest() != 0){
                        tv_baru.setText(""+count.getRequest());
                        tv_baru.setVisibility(View.VISIBLE);
                    } else {
                        tv_baru.setVisibility(View.GONE);
                    }

                    if (count.getProses() != 0){
                        tv_proses.setText(""+count.getProses());
                        tv_proses.setVisibility(View.VISIBLE);
                    } else {
                        tv_proses.setVisibility(View.GONE);
                    }

                    if (count.getDikembalikan() != 0){
                        tv_selesai.setText(""+count.getDikembalikan());
                        tv_selesai.setVisibility(View.VISIBLE);
                    } else {
                        tv_selesai.setVisibility(View.GONE);
                    }

                    tv_ulasan.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Count> call, Throwable t) {
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
