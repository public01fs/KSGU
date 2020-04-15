package com.application.ksgu;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.ksgu.Library.CustomSearchDialogCompat;
import com.application.ksgu.Model.DataDaerah;
import com.application.ksgu.Model.Login;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_AGEN;
import static com.application.ksgu.Cons.KEY_ALAMAT;
import static com.application.ksgu.Cons.KEY_DAERAH;
import static com.application.ksgu.Cons.KEY_DAERAH_ID;
import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_ID;
import static com.application.ksgu.Cons.KEY_NAME;
import static com.application.ksgu.Cons.KEY_NPWP;
import static com.application.ksgu.Cons.KEY_TELEPON;
import static com.application.ksgu.Cons.KEY_TOKEN;

public class EditProfileActivity extends AppCompatActivity {

    EditText et_email,et_nama,et_npwp,et_alamat,et_kota,et_telepon,et_password,et_confirm;
    EditText et_perusahaan,et_alamatp,et_kotap;
    HashMap<String, String> getLogin;
    SessionManager sessionManager;
    FloatingActionButton btn_save,btn_change;
    int daerah_id;
    int agen_id;
    int id;
    Uri photo;
    ImageView iv_photo;
    List<DataDaerah> dataDaerahs;
    SweetAlertDialog sweetAlertDialog;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_email            = findViewById(R.id.et_email);
        et_nama             = findViewById(R.id.et_nama);
        et_npwp             = findViewById(R.id.et_npwp);
        et_alamat           = findViewById(R.id.et_alamat);
        et_kota             = findViewById(R.id.et_kota);
        et_telepon          = findViewById(R.id.et_telepon);
        et_password         = findViewById(R.id.et_password);
        et_confirm          = findViewById(R.id.et_confirm);
        et_perusahaan       = findViewById(R.id.et_perusahaan);
        et_alamatp          = findViewById(R.id.et_alamatp);
        et_kotap            = findViewById(R.id.et_kotap);
        btn_save            = findViewById(R.id.btn_save);
        btn_change          = findViewById(R.id.btn_change);
        iv_photo            = findViewById(R.id.iv_photo);
        sessionManager      = new SessionManager(this);
        sweetAlertDialog    = new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        setLayout();

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah anda yakin ingin menyimpannya ?")
                        .setConfirmText("Iya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                updateProfile();
                            }
                        })
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        et_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataDaerahs != null) {
                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(EditProfileActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    daerah_id = item.getId();
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

        et_kotap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataDaerahs != null) {
                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(EditProfileActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    et_kotap.setText(item.getTitle());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    getDaerah1();
                }
            }
        });
    }

    private void setLayout(){
        getLogin        = sessionManager.getLogin();
        et_email.setText(getLogin.get(KEY_EMAIL));
        et_nama.setText(getLogin.get(KEY_NAME));
        et_npwp.setText(getLogin.get(KEY_NPWP));
        et_alamat.setText(getLogin.get(KEY_ALAMAT));
        et_kota.setText(getLogin.get(KEY_DAERAH));
        et_telepon.setText(getLogin.get(KEY_TELEPON));
        daerah_id       = Integer.valueOf(getLogin.get(KEY_DAERAH_ID));
        id              = Integer.valueOf(getLogin.get(KEY_ID));
        token           = getLogin.get(KEY_TOKEN);
    }

    private void updateProfile(){
        showpDialog();
        MultipartBody.Part photos= null;
        RequestBody nama        = RequestBody.create(MediaType.parse("text/plain"), check(et_nama.getText().toString()));
        RequestBody npwp        = RequestBody.create(MediaType.parse("text/plain"), check(et_npwp.getText().toString()));
        RequestBody pass        = RequestBody.create(MediaType.parse("text/plain"), check(et_password.getText().toString()));
        RequestBody confirm     = RequestBody.create(MediaType.parse("text/plain"), check(et_confirm.getText().toString()));
        RequestBody alamat      = RequestBody.create(MediaType.parse("text/plain"), et_alamat.getText().toString());
        RequestBody daerah      = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(daerah_id));
        RequestBody telepon     = RequestBody.create(MediaType.parse("text/plain"), check(et_telepon.getText().toString()));
        RequestBody agen_ids    = RequestBody.create(MediaType.parse("text/plain"), check(String.valueOf(agen_id)));
        RequestBody agen_nama   = RequestBody.create(MediaType.parse("text/plain"), check(et_perusahaan.getText().toString()));
        RequestBody agen_alamat = RequestBody.create(MediaType.parse("text/plain"), check(et_alamatp.getText().toString()));
        RequestBody agen_daerah = RequestBody.create(MediaType.parse("text/plain"), check(et_kotap.getText().toString()));
        RequestBody ids         = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
        if (photo != null){
            RequestBody foto        = getRequestBodyFromURI(photo.toString());
            photos                   = MultipartBody.Part.createFormData("ktp", getFileName(photo), foto);
        }
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,token);
        Call<Login> call            = apiInterface.updateProfile(photos,pass,confirm,nama,npwp,alamat,daerah,telepon,agen_ids,agen_nama,agen_alamat,agen_daerah,ids);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                hidepDialog();

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hidepDialog();
            }
        });
    }

    private String check(String data){
        if (TextUtils.isEmpty(data)){
            return  "";
        } else {
            return data;
        }
    }

    private RequestBody getRequestBodyFromURI(String uriString){
        try {
            Uri uri = Uri.parse(uriString);
            File f = new File(uri.getPath());
            ExifInterface ei = null;
            try {
//                        ei = new ExifInterface(uri.getPath());
                ei = new ExifInterface(getFilePathForN(uri,EditProfileActivity.this));
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
            new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
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
            new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    private void pickPhoto(){
        RxPermissions rxPermissions = new RxPermissions(EditProfileActivity.this);
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
                if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","WRITE_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","CAMERA : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.CAMERA);
                }
                if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","READ_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                Log.i("debugs","permissionCount: "+permissionCount);
                if (permissionCount>=2){
                    Matisse.from(EditProfileActivity.this)
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
        RxPermissions rxPermissions = new RxPermissions(EditProfileActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            if (Matisse.obtainResult(data).size()>0){
                photo = Matisse.obtainResult(data).get(0);
                Glide.with(getApplicationContext())
                        .load(photo)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .placeholder(R.drawable.ic_empty)
                        .error(R.drawable.ic_empty)
                        .dontAnimate()
                        .into(iv_photo);
            }
        }
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

                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(EditProfileActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    daerah_id = item.getId();
                                    et_kota.setText(item.getTitle());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataDaerah>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(EditProfileActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDaerah1(){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<DataDaerah>> call = apiInterface.getDaerah();
        call.enqueue(new Callback<List<DataDaerah>>() {
            @Override
            public void onResponse(Call<List<DataDaerah>> call, Response<List<DataDaerah>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    dataDaerahs = response.body();

                    CustomSearchDialogCompat dialog = new CustomSearchDialogCompat(EditProfileActivity.this, "Kota",
                            "Pilih Kota", null, (ArrayList) dataDaerahs,
                            new SearchResultListener<DataDaerah>() {
                                @Override
                                public void onSelected(
                                        BaseSearchDialogCompat dialog,
                                        DataDaerah item, int position1
                                ) {
                                    et_kotap.setText(item.getTitle());
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.show();
                    dialog.getSearchBox().setTypeface(Typeface.SERIF);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataDaerah>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(EditProfileActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
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
