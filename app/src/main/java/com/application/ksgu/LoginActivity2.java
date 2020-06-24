package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Model.Login;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity2 extends AppCompatActivity {

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
    Button btn_daftar;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btn_signin          = findViewById(R.id.btn_signin);
        et_email            = findViewById(R.id.et_email);
        et_password         = findViewById(R.id.et_password);
        btn_daftar          = findViewById(R.id.btn_daftar);
        iv_back             = findViewById(R.id.iv_back);
        sessionManager      = new SessionManager(this);
        prefs               = getSharedPreferences("email",MODE_PRIVATE);
        editor              = prefs.edit();
        sweetAlertDialog    = new SweetAlertDialog(LoginActivity2.this, SweetAlertDialog.PROGRESS_TYPE);
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

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity2.this,RegisterActivity2.class));
                finish();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                            Intent i = new Intent(LoginActivity2.this, VerificationActivity.class);
                            startActivity(i);
                        } else {
                            new SweetAlertDialog(LoginActivity2.this, SweetAlertDialog.ERROR_TYPE)
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
//                        Intent intent = new Intent(LoginActivity2.this, Main2Activity.class);
//                        startActivity(intent);
//                        finish();
//                        Intent i = getApplicationContext().getPackageManager()
//                                .getLaunchIntentForPackage(getApplicationContext().getPackageName() );
//
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
//                        startActivity(i);
                        Intent i = new Intent(LoginActivity2.this, Main3Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
//                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity2.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hidepDialog();
                Toast.makeText(LoginActivity2.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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