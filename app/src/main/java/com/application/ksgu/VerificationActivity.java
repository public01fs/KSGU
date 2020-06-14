package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Model.Login;
import com.application.ksgu.Model.Resend;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.raycoarana.codeinputview.CodeInputView;
import com.raycoarana.codeinputview.OnCodeCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VerificationActivity extends AppCompatActivity {

    CodeInputView cv_code;
    private Handler mHandler = new Handler();
    SweetAlertDialog sweetAlertDialog;
    String confirm;
    Button btn_verifikasi;
    LinearLayout ll_resend;
    TextView tv_resend;
    SessionManager sessionManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        cv_code             = findViewById(R.id.cv_code);
        btn_verifikasi      = findViewById(R.id.btn_verifikasi);
        ll_resend           = findViewById(R.id.ll_resend);
        tv_resend           = findViewById(R.id.tv_resend);
        sessionManager      = new SessionManager(this);
        prefs               = getSharedPreferences("email",MODE_PRIVATE);
        editor              = prefs.edit();
        email               = prefs.getString("email","");
        sweetAlertDialog    = new SweetAlertDialog(VerificationActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        if (sessionManager.isResend()){
            ll_resend.setVisibility(View.VISIBLE);
        } else {
            ll_resend.setVisibility(View.GONE);
        }

        cv_code.addOnCompleteListener(new OnCodeCompleteListener() {
            @Override
            public void onCompleted(String code) {
                confirm = code;
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Make the input enable again so the user can change it
//                        cv_code.setEditable(true);
//
//                        //Show error
////                        otherCodeInput.setError("Your code is incorrect");
//                    }
//                }, 1000);
            }
        });

        btn_verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(confirm)){
                    verification(confirm);
                }
            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend(email);
            }
        });


    }

    private void verification(String code){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<Login> call     = apiInterface.verification(code,email);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    sessionManager.createLoginSession(response.body());
                    sessionManager.createOtpSession(false);
                    Intent intent = new Intent(VerificationActivity.this, Main3Activity.class);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 422){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(VerificationActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        if (jsonObject.getString("error").contains("expired")){
                            ll_resend.setVisibility(View.VISIBLE);
                            sessionManager.createResend(true);
                            cv_code.setEditable(true);
                        } else {
                            cv_code.setEditable(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    cv_code.setEditable(true);
                    Toast.makeText(VerificationActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hidepDialog();
                cv_code.setEditable(true);
                Toast.makeText(VerificationActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resend(String email){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<Resend> call     = apiInterface.resend(email);
        call.enqueue(new Callback<Resend>() {
            @Override
            public void onResponse(Call<Resend> call, Response<Resend> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    new SweetAlertDialog(VerificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText(response.body().getMessage())
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    ll_resend.setVisibility(View.GONE);
                                    sessionManager.createResend(false);
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(VerificationActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resend> call, Throwable t) {
                hidepDialog();
                Toast.makeText(VerificationActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
