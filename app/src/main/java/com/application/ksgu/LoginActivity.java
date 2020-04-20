package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Model.Login;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;

public class LoginActivity extends AppCompatActivity {

    Button btn_signin;
    TextView tv_daftar;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
    SessionManager sessionManager;
    SweetAlertDialog sweetAlertDialog;
    EditText et_email,et_password;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signin          = findViewById(R.id.btn_signin);
        tv_daftar           = findViewById(R.id.tv_daftar);
        et_email            = findViewById(R.id.et_email);
        et_password         = findViewById(R.id.et_password);
        sessionManager      = new SessionManager(this);
        prefs               = getSharedPreferences("email",MODE_PRIVATE);
        editor              = prefs.edit();
        sweetAlertDialog    = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()){
                    getLogin(et_email.getText().toString(),et_password.getText().toString());
                }
            }
        });

        tv_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

//        if(!hasPermissions(this, PERMISSIONS)){
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.isOTP()) {
            Intent Jump_to_login = new Intent(this, VerificationActivity.class);
            Jump_to_login.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Jump_to_login);
            finish();
            return;
        }
    }

    private void getLogin(final String email, String password){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<Login> call            = apiInterface.login(email,password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    if (response.body().getError() != null){
                        if (response.body().getError().contains("verifikasi")){
                            editor.putString("email",email);
                            editor.commit();
                            Intent i = new Intent(LoginActivity.this, VerificationActivity.class);
                            startActivity(i);
                        } else {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setContentText(response.body().getError())
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        sessionManager.createLoginSession(response.body());
//                        sessionManager.createOtpSession(false);
                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hidepDialog();
                Toast.makeText(LoginActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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

    private Boolean isValid(){
        if (TextUtils.isEmpty(et_email.getText().toString())){
            Toast.makeText(this, "Email harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())){
            Toast.makeText(this, "Password harap diisi", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
