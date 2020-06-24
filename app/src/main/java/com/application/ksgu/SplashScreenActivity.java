package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.application.ksgu.Model.Image;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_DURATION = 2000;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefs           = getSharedPreferences("layanan", Context.MODE_PRIVATE);
        editor          = prefs.edit();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent mainIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
//                Intent mainIntent = new Intent(SplashScreenActivity.this, Main3Activity.class);
//                SplashScreenActivity.this.startActivity(mainIntent);
//                SplashScreenActivity.this.finish();
//
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                getImage();
            }
        }, SPLASH_DISPLAY_DURATION);

    }

    private void getImage(){
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Image>> call      = apiInterface.getImage();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0) {
//                        loadImage(response.body());
                        editor.putString("foto",gson.toJson(response.body()));
                        editor.commit();
                        Intent mainIntent = new Intent(SplashScreenActivity.this, Main3Activity.class);
                        SplashScreenActivity.this.startActivity(mainIntent);
                        SplashScreenActivity.this.finish();

                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });
    }
}