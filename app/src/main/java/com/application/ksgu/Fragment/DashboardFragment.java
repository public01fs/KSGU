package com.application.ksgu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.MainActivity;
import com.application.ksgu.PermohonanActivity;
import com.application.ksgu.PermohonanListActivity;
import com.application.ksgu.R;
import com.application.ksgu.SessionManager;
import com.artcak.artcakbase.tools.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;

import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_NAME;

public class DashboardFragment extends Fragment {

    View view;
    SessionManager sessionManager;
    TextView tv_nama,tv_email;
    HashMap<String, String> getLogin;
    ImageView iv_photo;
    ProgressBar pb_loading;
    RelativeLayout rv_ppmkk,rv_kes_kapal,rv_pelaut,rv_ppk,rv_stb,rv_p3;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rv_ppmkk        = view.findViewById(R.id.rv_ppmkk);
        rv_kes_kapal    = view.findViewById(R.id.rv_kes_kapal);
        rv_pelaut       = view.findViewById(R.id.rv_pelaut);
        rv_ppk          = view.findViewById(R.id.rv_ppk);
        rv_stb          = view.findViewById(R.id.rv_stb);
        rv_p3           = view.findViewById(R.id.rv_p3);
        tv_nama         = view.findViewById(R.id.tv_nama);
        tv_email        = view.findViewById(R.id.tv_email);
        iv_photo        = view.findViewById(R.id.iv_photo);
        pb_loading      = view.findViewById(R.id.pbLoading);
        prefs           = getActivity().getSharedPreferences("layanan", Context.MODE_PRIVATE);
        editor          = prefs.edit();
        sessionManager  = new SessionManager(getContext());
        getLogin        = sessionManager.getLogin();

        setLayout();

        rv_ppmkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan PPMKK");
                editor.putString("data","PPMKK");
                editor.commit();
                startActivity(new Intent(getContext(), PermohonanActivity.class));
            }
        });

        rv_kes_kapal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan Keselamatan Kapal");
                editor.putString("data","KESELAMATAN KAPAL");
                editor.commit();
                startActivity(new Intent(getContext(),PermohonanActivity.class));
            }
        });

        rv_pelaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan Kepelautan");
                editor.putString("data","KEPELAUTAN");
                editor.commit();
                startActivity(new Intent(getContext(),PermohonanActivity.class));
            }
        });

        rv_ppk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan PPK");
                editor.putString("data","PPK");
                editor.commit();
                startActivity(new Intent(getContext(),PermohonanActivity.class));
            }
        });

        rv_stb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan STB");
                editor.putString("data","STB");
                editor.commit();
                startActivity(new Intent(getContext(),PermohonanActivity.class));
            }
        });

        rv_p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("title","Permohonan P3");
                editor.putString("data","P3");
                editor.commit();
                startActivity(new Intent(getContext(),PermohonanActivity.class));
            }
        });

        return view;
    }

    private void setLayout(){
        tv_nama.setText(getLogin.get(KEY_NAME));
        tv_email.setText(getLogin.get(KEY_EMAIL));
        if (getLogin.get(KEY_IMG) != null) {
            Glide.with(getContext())
                    .load(getLogin.get(KEY_IMG))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(iv_photo);
        } else {
            pb_loading.setVisibility(View.GONE);
            Glide.with(getContext())
                    .load(R.drawable.ic_empty)
                    .into(iv_photo);
        }
    }
}
