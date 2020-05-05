package com.application.ksgu.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.application.ksgu.Adapter.ItemCheckAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.DataManager;
import com.application.ksgu.EditProfileActivity;
import com.application.ksgu.Main2Activity;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataKirim;
import com.application.ksgu.Model.Kantor;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.Model.Save;
import com.application.ksgu.Model.UploadFotoModel;
import com.application.ksgu.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_upload_dokumen, container, false);
        rv_upload       = view.findViewById(R.id.rv_upload);
        prefs           = getActivity().getSharedPreferences("layanan",Context.MODE_PRIVATE);
        editor          = prefs.edit();
        sessionManager  = new SessionManager(getContext());
        getLogin        = sessionManager.getLogin();

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
                    menuList.add(new UploadFotoModel(dataKirim.getChecklists().get(i).getCEKLISTNAME()));
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

                        for (int i = 0; i < checklists.size(); i++) {
                            menuList.add(new UploadFotoModel(checklists.get(i).getCEKLISTNAME()));
                        }

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
                menuList.get(currentFile).setmUri(uris);
            }
            uploadFotoAdapter.notifyDataSetChanged();
        } else if (requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = Uri.parse("android.resource://com.application.ksgu/drawable/ic_file");
            List<Uri> uris = new ArrayList<>();
            uris.add(uri);
            menuList.get(currentFile).setmUri(uris);
            uploadFotoAdapter.notifyDataSetChanged();
        }
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
        List<String> nota_id = new ArrayList<>();
        List<String> parent_id = new ArrayList<>();

        for (int i = 0; i < dataKirim.getDataCheck().size(); i++) {
            nota_id.add(String.valueOf(dataKirim.getDataCheck().get(i).getNOTAID()));
            if ((!TextUtils.isEmpty(String.valueOf(dataKirim.getDataCheck().get(i).getNOTAID())))) {
                parent_id.add(String.valueOf(dataKirim.getDataCheck().get(i).getPARENTID()));
            } else {
                parent_id.add("");
            }
        }

        ApiInterface apiInterface       = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<Save> call                 = apiInterface.savePermohonan(dataKirim.getIdkantor(),dataKirim.getJenis_id(),dataKirim.getSurat_no(),dataKirim.getSurat_hal(),dataKirim.getSurat_pengirim(),dataKirim.getSurat_pengirim_kota(),dataKirim.getSurat_npwp(),
                dataKirim.getSurat_tgl(),dataKirim.getRequire_check(),dataKirim.getKapal_id(),dataKirim.getKapal_name(),dataKirim.getKapal_gt(),dataKirim.getKapal_cs(),dataKirim.getKapal_bendera(),dataKirim.getKapal_pemilik(),dataKirim.getKapal_posisi(),dataKirim.getNamaprsh(),
                dataKirim.getAlamatprsh(),dataKirim.getIdentitasprsh(),dataKirim.getJmlkapal(),dataKirim.getTotalgt(),dataKirim.getIdpelaut(),dataKirim.getNamapelaut(),dataKirim.getKodepelaut(),dataKirim.getTempatlahir(),dataKirim.getTgllahir(),dataKirim.getUmur(),dataKirim.getJk(),
                dataKirim.getStatuspelaut(),dataKirim.getSertifikat(),dataKirim.getFotopelaut(),nota_id,parent_id);
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
}
