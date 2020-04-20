package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PermohonanListActivity extends AppCompatActivity {

    FloatingActionButton fab_add;
    TextView tv_title;
    SharedPreferences prefs;
    String title;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_list);

        fab_add     = findViewById(R.id.fab_add);
        tv_title    = findViewById(R.id.tv_title);
        iv_back     = findViewById(R.id.iv_back);
        prefs       = getSharedPreferences("layanan",MODE_PRIVATE);
        title       = prefs.getString("title","");

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PermohonanListActivity.this,PermohonanActivity.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
