package com.application.ksgu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.Disposable;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Fragment.UploadDokumenFragment;
import com.application.ksgu.Library.CustomSearchDialogCompat;
import com.application.ksgu.Model.DataDaerah;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    EditText et_nama,et_alamat,et_kota,et_email,et_telepon,et_password;
    List<DataDaerah> dataDaerahs;
    DataDaerah dataDaerah;
    SweetAlertDialog sweetAlertDialog;
    Button btnverifyCaptcha;
    String TAG = RegisterActivity.class.getSimpleName();
    LinearLayout ll_foto;
    TextView tv_foto;
    Uri ktp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register        = findViewById(R.id.btn_register);
        et_nama             = findViewById(R.id.et_nama);
        et_alamat           = findViewById(R.id.et_alamat);
        et_kota             = findViewById(R.id.et_kota);
        et_email            = findViewById(R.id.et_email);
        et_telepon          = findViewById(R.id.et_telepon);
        et_password         = findViewById(R.id.et_password);
        ll_foto             = findViewById(R.id.ll_photo);
        tv_foto             = findViewById(R.id.tv_ktp);
        btnverifyCaptcha    = findViewById(R.id.button);

        sweetAlertDialog    = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,PermohonanActivity.class));
//                if (isValid()){
//                    kirimRegister();
//                }
            }
        });

        btnverifyCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captcha();
            }
        });

        et_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataDaerahs != null) {
                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(RegisterActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    dataDaerah = item;
                                    et_kota.setText(item.getTitle());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    getDaerah();
                }
            }
        });

        ll_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto();
            }
        });

    }

    private void pickPhoto(){
        RxPermissions rxPermissions = new RxPermissions(RegisterActivity.this);
        rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new io.reactivex.Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.i("debugs","pickPhotoonNext aBoolean: "+aBoolean);
                int permissionCount = 0;
                if (ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","WRITE_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","CAMERA : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.CAMERA);
                }
                if (ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","READ_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                Log.i("debugs","permissionCount: "+permissionCount);
                if (permissionCount>=2){
                    Matisse.from(RegisterActivity.this)
                            .choose(MimeType.allOf())
                            .countable(true)
                            .capture(true)
                            .maxSelectable(1)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .captureStrategy(new CaptureStrategy(true, "com.application.ksgu.provider"))
                            .imageEngine(new GlideEngine())
                            .forResult(200);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void requestPermission(String permission){
        RxPermissions rxPermissions = new RxPermissions(RegisterActivity.this);
        rxPermissions.request(permission).subscribe(new io.reactivex.Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getDaerah(){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<DataDaerah>> call = apiInterface.getDaerah();
        call.enqueue(new Callback<List<DataDaerah>>() {
            @Override
            public void onResponse(Call<List<DataDaerah>> call, Response<List<DataDaerah>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    dataDaerahs = response.body();

                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(RegisterActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    dataDaerah = item;
                                    et_kota.setText(item.getTitle());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataDaerah>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void captcha(){
        SafetyNet.getClient(this).verifyWithRecaptcha("6LdRBuYUAAAAADwOIvJ99Khw3yHpldTswK-ciMpv")
                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                        if (!recaptchaTokenResponse.getTokenResult().isEmpty()){
                            verify(recaptchaTokenResponse.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                        }
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

    private void verify(String response){
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<ResponseBody> call     = apiInterface.verifiy("6LdRBuYUAAAAAJq-3heDBvMZ0TUQ1JwXp2dpRe_Q",response);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject   = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("success")){
                            Toast.makeText(RegisterActivity.this, "true", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "false", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private boolean isValid(){
        if (TextUtils.isEmpty(et_nama.getText().toString())){
            Toast.makeText(this, "Nama harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_email.getText().toString())){
            Toast.makeText(this, "Email harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_password.getText().toString())){
            Toast.makeText(this, "Password harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_alamat.getText().toString())){
            Toast.makeText(this, "Alamat harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_kota.getText().toString())){
            Toast.makeText(this, "Kota harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_telepon.getText().toString())){
            Toast.makeText(this, "Telepon harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ktp == null){
            Toast.makeText(this, "KTP harap diisi", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            if (Matisse.obtainResult(data).size()>0){
                ktp = Matisse.obtainResult(data).get(0);
                tv_foto.setText(getFileName(ktp));
            }
        }
    }

    private RequestBody getRequestBodyFromURI(String uriString){
        try {
            Uri uri = Uri.parse(uriString);
            File f = new File(uri.getPath());
            ExifInterface ei = null;
            try {
//                        ei = new ExifInterface(uri.getPath());
                ei = new ExifInterface(getFilePathForN(uri,RegisterActivity.this));
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmapOri = BitmapFactory.decodeStream(inputStream);
            if (inputStream!=null){
                inputStream.close();
            }
            if (bitmapOri!=null){
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmapOri, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmapOri, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmapOri, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmapOri;
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Boolean isCompressed = rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                byte[] byteArray = stream.toByteArray();
                return RequestBody.create(MediaType.parse("image/jpg"), byteArray);
            }else{
                return null;
            }
        } catch (FileNotFoundException e) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Terjadi Kesalahan Jaringan")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    })
                    .show();
            e.printStackTrace();
        } catch (IOException e) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Terjadi Kesalahan Jaringan")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    })
                    .show();
            e.printStackTrace();
        }
        return null;
    }

    private static String getFilePathForN(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void kirimRegister(){
        MultipartBody.Part photo = null;
        showpDialog();
        RequestBody nama        = RequestBody.create(MediaType.parse("text/plain"), et_nama.getText().toString());
        RequestBody email       = RequestBody.create(MediaType.parse("text/plain"), et_email.getText().toString());
        RequestBody pass        = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
        RequestBody alamat      = RequestBody.create(MediaType.parse("text/plain"), et_alamat.getText().toString());
        RequestBody daerah      = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(dataDaerah.getId()));
        RequestBody telepon     = RequestBody.create(MediaType.parse("text/plain"), et_telepon.getText().toString());
        RequestBody foto        = getRequestBodyFromURI(ktp.toString());
        photo                   = MultipartBody.Part.createFormData("ktp", getFileName(ktp), foto);

        ApiInterface api        = ServiceGenerator.createService(ApiInterface.class);
        Call<ResponseBody> call = api.saveRegister(nama,email,pass,pass,alamat,daerah,telepon,photo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("Registrasi Berhasil")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    finish();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hidepDialog();
                Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
