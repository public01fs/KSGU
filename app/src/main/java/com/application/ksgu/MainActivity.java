package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.CustomMenuAdapter;
import com.artcak.artcakbase.model.ItemMenu;
import com.artcak.artcakbase.recycleview.GridSpacingItemDecoration;
import com.artcak.artcakbase.recycleview.RecyclerViewClickListener;
import com.artcak.artcakbase.recycleview.RecyclerViewTouchListener;
import com.artcak.artcakbase.tools.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_NAME;

public class MainActivity extends AppCompatActivity {

    CardView cv_permohonan,cv_profile;
    SessionManager sessionManager;
    TextView tv_nama,tv_email;
    HashMap<String, String> getLogin;
    ImageView iv_photo;
    ProgressBar pb_loading;
    Tools tools = new Tools(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv_permohonan   = findViewById(R.id.cv_permohonan);
        cv_profile      = findViewById(R.id.cv_profile);
        tv_nama         = findViewById(R.id.tv_nama);
        tv_email        = findViewById(R.id.tv_email);
        iv_photo        = findViewById(R.id.iv_photo);
        pb_loading      = findViewById(R.id.pbLoading);
        sessionManager  = new SessionManager(this);

        if (!sessionManager.isLoggedIn()){
            Intent Jump_to_login = new Intent(this, LoginActivity.class);
            Jump_to_login.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Jump_to_login);
            finish();
            return;
        }

        cv_permohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PermohonanListActivity.class));
            }
        });

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });
    }

    private void setLayout(){
        getLogin        = sessionManager.getLogin();
        tv_nama.setText(getLogin.get(KEY_NAME));
        tv_email.setText(getLogin.get(KEY_EMAIL));
        if (getLogin.get(KEY_IMG) != null) {
            Glide.with(this)
                    .load(getLogin.get(KEY_IMG))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
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
            Glide.with(this)
                    .load(R.drawable.ic_empty)
                    .into(iv_photo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLayout();
    }
}
