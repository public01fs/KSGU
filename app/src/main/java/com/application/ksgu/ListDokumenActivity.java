package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.DokumenListAdapter;
import com.application.ksgu.Model.Document;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.artcak.artcakbase.recycleview.GridSpacingItemDecoration;
import com.artcak.artcakbase.tools.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.application.ksgu.Cons.KEY_TOKEN;

public class ListDokumenActivity extends AppCompatActivity {

    TextView tv_title;
    RecyclerView rv_dokumen;
    DokumenListAdapter dokumenListAdapter;
    SweetAlertDialog sweetAlertDialog;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    Tools tools = new Tools(this);
    private static final String TAG = "ListDokumenActivity";
    ImageView iv_back;
    SearchView searchView;
    List<Document> documents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokumen);

        tv_title            = findViewById(R.id.tv_title);
        rv_dokumen          = findViewById(R.id.rv_dokumen);
        iv_back             = findViewById(R.id.iv_back);
        searchView          = findViewById(R.id.search);
        sessionManager      = new SessionManager(this);
        getLogin            = sessionManager.getLogin();
        sweetAlertDialog    = new SweetAlertDialog(ListDokumenActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);

        searchView.setMaxWidth(Integer.MAX_VALUE);

        if (getIntent().getExtras() != null){
            tv_title.setText(getIntent().getStringExtra("title"));
            getDokumen(getIntent().getStringExtra("bidang_id"));
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                return false;
            }
        });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                beginSearch(newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.

                // Log.e("Query",query);


                return false;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    public void beginSearch(String query) {
        dokumenListAdapter.changeDataSet(searchData(query));
    }

    private List<Document> searchData(String query) {
        List<Document> data = new ArrayList<>();
        for (Document j : documents) {
            String dokumen  = j.getDokumen().toLowerCase();
            String nomor    = j.getNomorDokumen().toLowerCase();
            if (dokumen.contains(query.toLowerCase()) || nomor.contains(query.toLowerCase())) {
                data.add(j);
            }
        }

        return data;
    }

    private void showpDialog() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }

    private void hidepDialog() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }

    private void getDokumen(String bidang_id){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<List<Document>> call   = apiInterface.getDocument(bidang_id);
        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    documents   = response.body();
                    if (response.body().size() > 0){
                        dokumenListAdapter = new DokumenListAdapter(ListDokumenActivity.this,response.body());
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ListDokumenActivity.this, 1);
                        rv_dokumen.addItemDecoration(new GridSpacingItemDecoration(1, tools.dpToPx(0), true));
                        rv_dokumen.setLayoutManager(mLayoutManager);
                        rv_dokumen.setItemAnimator(new DefaultItemAnimator());
                        rv_dokumen.setAdapter(dokumenListAdapter);
                        dokumenListAdapter.setOnItemClickListener(new DokumenListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, Document obj, int position) {
                                downloadDocument(obj.getFile());
                            }
                        });
                    }
                } else {
                    Toast.makeText(ListDokumenActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                hidepDialog();
                Toast.makeText(ListDokumenActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadDocument(final String url){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class,getLogin.get(KEY_TOKEN));
        Call<ResponseBody> call     = apiInterface.downloadDocument(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    String[] nama = url.split("/");
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(),nama[4]);
                    if (writtenToDisk){
                        Toast.makeText(ListDokumenActivity.this, "Download Sukses", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListDokumenActivity.this, "Download Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListDokumenActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hidepDialog();
                Toast.makeText(ListDokumenActivity.this, "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String nama) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + nama);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
