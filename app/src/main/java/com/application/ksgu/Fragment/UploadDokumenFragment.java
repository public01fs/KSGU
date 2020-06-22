package com.application.ksgu.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.EditProfileActivity;
import com.application.ksgu.Library.FileUtils;
import com.application.ksgu.Main2Activity;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.ChecklistKirim;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.FileBerkas;
import com.application.ksgu.Model.Kantor;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.Model.Save;
import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
import com.application.ksgu.RegisterActivity;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;
import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
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

import static android.app.Activity.RESULT_OK;
import static com.application.ksgu.Cons.KEY_TOKEN;

public class UploadDokumenFragment extends Fragment implements BlockingStep {

    public static UploadDokumenFragment newInstance() {
        return new UploadDokumenFragment();
    }

    private DataManager dataManager;
    View view;
    RecyclerView rv_upload;
    Gson gson = new Gson();
    DataKirim dataKirim;
    UploadFotoAdapter uploadFotoAdapter;
    List<UploadFotoModel> menuList = new ArrayList<>();
    List<Checklist> checklists;
    int currentFile;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Layanan layanan;

    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    SweetAlertDialog sweetAlertDialog;
//    List<FileBerkas> files = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_upload_dokumen, container, false);
        rv_upload           = view.findViewById(R.id.rv_upload);
        prefs               = getActivity().getSharedPreferences("layanan",Context.MODE_PRIVATE);
        editor              = prefs.edit();
        sessionManager      = new SessionManager(getContext());
        getLogin            = sessionManager.getLogin();
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
//        getActivity().finish();
//        savePermohonan(dataKirim);
        getDataKantor();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        dataKirim       = gson.fromJson(dataManager.getData(), DataKirim.class);
        if (TextUtils.isEmpty(prefs.getString("pelayanan",""))){
            editor.putString("pelayanan",gson.toJson(dataKirim.getLayanan()));
            editor.apply();
        } else {
            layanan = gson.fromJson(prefs.getString("pelayanan",""),Layanan.class);

            if (layanan.getJENISID() != dataKirim.getLayanan().getJENISID()){
                if (menuList != null){
                    menuList.clear();
                }
                editor.putString("pelayanan",gson.toJson(dataKirim.getLayanan()));
                editor.apply();
            }
        }

        if (dataKirim.getChecklists().size() > 0){
            if (menuList.size() == 0){
                for (int i = 0; i < dataKirim.getChecklists().size(); i++) {
                    FileBerkas file = new FileBerkas();
                    file.setFILEEXTENSION("");
                    file.setFILENAME("");
                    file.setFILESIZE(0);
                    file.setSERVERFILE("");
                    ChecklistKirim checklistKirim = new ChecklistKirim();
                    checklistKirim.setCEKLIST_NAME(dataKirim.getChecklists().get(i).getCEKLISTNAME());
                    checklistKirim.setCEKLIST_ID(String.valueOf(dataKirim.getChecklists().get(i).getCEKLISTID()));
                    menuList.add(new UploadFotoModel(dataKirim.getChecklists().get(i).getCEKLISTNAME(),file,checklistKirim));
                }
            }

            uploadFotoAdapter = new UploadFotoAdapter(menuList);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
//                        rv_upload.addItemDecoration(new GridSpacingItemDecoration(2, tools.dpToPx(8), true));
            rv_upload.setLayoutManager(mLayoutManager);
            rv_upload.setItemAnimator(new DefaultItemAnimator());
            rv_upload.setAdapter(uploadFotoAdapter);

            uploadFotoAdapter.setOnItemClickListener(new UploadFotoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, UploadFotoModel obj, final int position) {
                    final CharSequence[] items = {"Pilih foto", "Pilih file", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Tambahkan Foto/File");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Pilih foto")) {
                                currentFile = position;
                                pickPhoto();
//                                takePhoto();
                            } else if (items[item].equals("Pilih file")) {
                                currentFile = position;
                                showFileChooser();
//                                pickPhoto();
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManager) {
            dataManager = (DataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void setRecycleView(int id){
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Checklist>> call  = apiInterface.getCheckList(id);
        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        checklists  = response.body();

//                        for (int i = 0; i < checklists.size(); i++) {
//                            menuList.add(new UploadFotoModel(checklists.get(i).getCEKLISTNAME()));
//                        }

                        uploadFotoAdapter = new UploadFotoAdapter(menuList);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
//                        rv_upload.addItemDecoration(new GridSpacingItemDecoration(2, tools.dpToPx(8), true));
                        rv_upload.setLayoutManager(mLayoutManager);
                        rv_upload.setItemAnimator(new DefaultItemAnimator());
                        rv_upload.setAdapter(uploadFotoAdapter);
                    } else {
//                        cv_layanan.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {

            }
        });
    }

    private void pickPhoto(){
        RxPermissions rxPermissions = new RxPermissions(getActivity());
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
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","WRITE_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","CAMERA : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(android.Manifest.permission.CAMERA);
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Log.i("debugs","READ_EXTERNAL_STORAGE : GRANTED ");
                    permissionCount++;
                }else{
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                Log.i("debugs","permissionCount: "+permissionCount);
                if (permissionCount>=2){
                    Matisse.from(UploadDokumenFragment.this)
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
        RxPermissions rxPermissions = new RxPermissions(getActivity());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            if (Matisse.obtainResult(data).size()>0){
                List<Uri> uris = new ArrayList<>();
                uris.add(Matisse.obtainResult(data).get(0));
                uploadSurat(Matisse.obtainResult(data).get(0),currentFile,"foto");
                menuList.get(currentFile).setmUri(uris);

            }
            uploadFotoAdapter.notifyDataSetChanged();
        } else if (requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = Uri.parse("android.resource://com.application.ksgu/drawable/ic_file");
            List<Uri> uris = new ArrayList<>();
            uris.add(uri);
            uploadSurat(data.getData(),currentFile,"pdf");
            menuList.get(currentFile).setmUri(uris);
            uploadFotoAdapter.notifyDataSetChanged();
        }
    }

    private RequestBody getRequestBodyFromURI(String uriString){
        try {
            Uri uri = Uri.parse(uriString);
            File f = new File(uri.getPath());
            ExifInterface ei = null;
            try {
//                        ei = new ExifInterface(uri.getPath());
                ei = new ExifInterface(getFilePathForN(uri, getContext()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
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
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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

    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),1);

        String[] mimeTypes = {"application/pdf"};

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 1);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putString("layanan", gson.toJson(dataKirim.getLayanan()));
//        super.onSaveInstanceState(outState);
//    }

    private void savePermohonan(DataKirim dataKirim){
        List<MultipartBody.Part> nota_id = new ArrayList<>();
        List<MultipartBody.Part> parent_id = new ArrayList<>();
//        List<String> nota_id = new ArrayList<>();
//        List<String> parent_id = new ArrayList<>();

        if (dataKirim.getDataCheck() != null){
            for (int i = 0; i < dataKirim.getDataCheck().size(); i++) {
//            nota_id.add(String.valueOf(dataKirim.getDataCheck().get(i).getNOTAID()));
                nota_id.add(MultipartBody.Part.createFormData("NOTA_ID[]", String.valueOf(dataKirim.getDataCheck().get(i).getNOTAID())));
                if ((!TextUtils.isEmpty(String.valueOf(dataKirim.getDataCheck().get(i).getNOTAID())))) {
                    parent_id.add(MultipartBody.Part.createFormData("PARENT_ID[]",String.valueOf(dataKirim.getDataCheck().get(i).getPARENTID())));
                } else {
                    parent_id.add(MultipartBody.Part.createFormData("PARENT_ID[]",""));
                }
            }
        }

        List<FileBerkas> berkas = new ArrayList<>();
        List<ChecklistKirim> kirim = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            berkas.add(menuList.get(i).getFileBerkas());
            kirim.add(menuList.get(i).getChecklistKirim());
        }

        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<Save> call                 =
                apiInterface.savePermohonan(
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(dataKirim.getIdkantor())),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getJenis_id()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_no()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_hal()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_pengirim()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_pengirim_kota()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_npwp()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSurat_tgl()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getRequire_check()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_id()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_name()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_gt()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_cs()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_bendera()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_pemilik()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKapal_posisi()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getNamaprsh()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getAlamatprsh()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getIdentitasprsh()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getJmlkapal()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getTotalgt()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getIdpelaut()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getNamapelaut()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getKodepelaut()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getTempatlahir()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getTgllahir()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getUmur()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getJk()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getStatuspelaut()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getSertifikat()),
                RequestBody.create(MediaType.parse("text/plain"), dataKirim.getFotopelaut()),
                nota_id,parent_id,
                RequestBody.create(MediaType.parse("multipart/form-data"), gson.toJson(berkas)),
                RequestBody.create(MediaType.parse("multipart/form-data"), gson.toJson(kirim)));
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.body().isStatus()){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("Permohonan Berhasil Disimpan")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    getActivity().finish();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {

            }
        });
    }

    private void getDataKantor(){
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<Kantor> call               = apiInterface.getKantor();
        call.enqueue(new Callback<Kantor>() {
            @Override
            public void onResponse(Call<Kantor> call, Response<Kantor> response) {
                if (response.isSuccessful()){
                    dataKirim.setIdkantor(String.valueOf(response.body().getIDKANTOR()));
                    savePermohonan(dataKirim);
                }
            }

            @Override
            public void onFailure(Call<Kantor> call, Throwable t) {

            }
        });
    }

    private void uploadSurat(Uri uri, final int position,String kirim){
        showpDialog();
        MultipartBody.Part data         = null;
        RequestBody foto                = null;
        if (kirim.equals("pdf")){
//            File f = new File(FileUtils.getPath(getContext(),uri));
            File f = new File(getFilePathForN(uri,getContext()));
//            File f = new File(uri.getPath());
            foto                = RequestBody.create(MediaType.parse("application/pdf"), f);
        } else {
            foto                = getRequestBodyFromURI(uri.toString());
        }

        data                            = MultipartBody.Part.createFormData("file", getFileName(uri), foto);
        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<FileBerkas> call           = apiInterface.uploadSurat(data);
        call.enqueue(new Callback<FileBerkas>() {
            @Override
            public void onResponse(Call<FileBerkas> call, Response<FileBerkas> response) {
                hidepDialog();
                if (response.isSuccessful()){
//                    files.get(position).setSERVERFILE(response.body().getSERVERFILE());
//                    files.get(position).setFILESIZE(response.body().getFILESIZE());
//                    files.get(position).setFILENAME(response.body().getFILENAME());
//                    files.get(position).setFILEEXTENSION(response.body().getFILEEXTENSION());
                    menuList.get(position).setFileBerkas(response.body());
//                    dataKirim.setFiles(files);
                    Toast.makeText(getContext(), "Surat Berhasil Terupload", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Surat Gagal Terupload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FileBerkas> call, Throwable t) {
                hidepDialog();
                Log.e("error",t.getMessage());
                Toast.makeText(getContext(), "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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

    private void showpDialog() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }

    private void hidepDialog() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }
}
