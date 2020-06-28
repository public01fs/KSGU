package com.application.ksgu.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.ksgu.Adapter.LampiranAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.SuratLampiranItem;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.shockwave.pdfium.PdfDocument;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.grantland.widget.AutofitTextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LampiranFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener {

    View view;
    RecyclerView rv_lampiran;
    SharedPreferences prefs;
    Gson gson = new Gson();
    List<SuratLampiranItem> suratLampiranItems = new ArrayList<>();
    LampiranAdapter lampiranAdapter;
    PDFView pdfView;
    ProgressBar pb_pdf;
    private Integer pageNumber = 0;
    SweetAlertDialog sweetAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_lampiran, container, false);
        rv_lampiran         = view.findViewById(R.id.rv_lampiran);
        prefs               = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);
        initData(gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class).getSuratLampiran());
        return view;
    }

    private void initData(List<SuratLampiranItem> suratLampiranItemList){
        suratLampiranItems  = suratLampiranItemList;

        lampiranAdapter = new LampiranAdapter(suratLampiranItems);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        rv_lampiran.setLayoutManager(mLayoutManager);
        rv_lampiran.setItemAnimator(new DefaultItemAnimator());
        rv_lampiran.setAdapter(lampiranAdapter);

        lampiranAdapter.setOnItemClickListener(new LampiranAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SuratLampiranItem obj, int position) {
                if (obj.getPATH() != null && !obj.getPATH().equals("")){
                    if (obj.getPATH().substring(obj.getPATH().lastIndexOf(".")).equals(".jpg") || obj.getPATH().substring(obj.getPATH().lastIndexOf(".")).equals(".png") || obj.getPATH().substring(obj.getPATH().lastIndexOf(".")).equals(".jpeg")){
                        dialog(obj.getSERVERFILE()+obj.getPATH(),obj.getCEKLISTNAME());
                    } else {
                        down(obj.getSERVERFILE()+obj.getPATH(),obj.getCEKLISTNAME());
                    }
                }
            }
        });
    }

    private void dialog(String gambar_galery, String nama_galery) {
        final int[] rotat = {0};
        final Dialog dg = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dg.setContentView(R.layout.dialog_galery);
        final PhotoView gambar      = (PhotoView) dg.findViewById(R.id.iv_photo);
        ImageView cancel            = (ImageView) dg.findViewById(R.id.iv_cancel);
        ImageView rotate            = (ImageView) dg.findViewById(R.id.iv_rotate);
        ImageView rotate1           = (ImageView) dg.findViewById(R.id.iv_rotate1);
        AutofitTextView tv_judul    = (AutofitTextView) dg.findViewById(R.id.tv_judul);
        tv_judul.setText(nama_galery);
        Glide.with(getContext())
                .load(gambar_galery)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.no_photo)
//                .transform(new MyTransformation(getBaseContext(), 90))
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gambar);
        dg.show();
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int w = gambar.getWidth();
                int h = gambar.getHeight();

                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) gambar.getLayoutParams();
                lp.height = w;
                lp.width = h;
                gambar.requestLayout();
                gambar.setRotation(rotat[0] +=90);
            }
        });
        rotate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int w = gambar.getWidth();
                int h = gambar.getHeight();

                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) gambar.getLayoutParams();
                lp.height = w;
                lp.width = h;
                gambar.requestLayout();
                gambar.setRotation(rotat[0] -= 90);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dg.dismiss();
            }
        });
    }

    private void dialog_pdf(InputStream inputStream, String nama_galery) {
        final Dialog dg = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dg.setContentView(R.layout.dialog_pdf);
        pdfView = (PDFView) dg.findViewById(R.id.pdfView);
        pb_pdf  = (ProgressBar) dg.findViewById(R.id.pb_pdf);
        ImageView cancel = (ImageView) dg.findViewById(R.id.iv_cancel);
        AutofitTextView tv_judul = (AutofitTextView) dg.findViewById(R.id.tv_judul);
        tv_judul.setText(nama_galery);

        pdfView.fromStream(inputStream)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pb_pdf.setVisibility(View.GONE);
                    }
                })
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .spacing(10) // in dp
                .load();

        dg.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dg.dismiss();
            }
        });
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        getActivity().setTitle(String.format("%s %s / %s", "PDF Reader", page + 1, pageCount));
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e("TAG", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    private void down(String url, final String judul){
        showpDialog();
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<ResponseBody> download = apiInterface.download_pdf(url);
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hidepDialog();
                if (response.isSuccessful()){
                    dialog_pdf(response.body().byteStream(),judul);
                } else {
                    Toast.makeText(getContext(), "Terjadi kesalahan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hidepDialog();
                Toast.makeText(getContext(), "Terjadi kesalahan pada koneksi anda, ulangi lagi!", Toast.LENGTH_SHORT).show();
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
