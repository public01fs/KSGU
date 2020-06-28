package com.application.ksgu.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.ksgu.Adapter.DetailDataAdapter;
import com.application.ksgu.Model.Billing;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
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

public class BillingFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener {

    View view;
    RecyclerView rv_billing;
    SharedPreferences prefs;
    Gson gson = new Gson();
    DetailDataAdapter detailDataAdapter;
    LinearLayout ll_button;
    Button btn_nota,btn_kuitansi;
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
        view                = inflater.inflate(R.layout.fragment_billing, container, false);
        rv_billing          = view.findViewById(R.id.rv_billing);
        btn_kuitansi        = view.findViewById(R.id.bt_kuitansi);
        btn_nota            = view.findViewById(R.id.bt_nota);
        ll_button           = view.findViewById(R.id.ll_button);
        prefs               = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        sweetAlertDialog    = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#000080"));
        sweetAlertDialog.setTitleText("Mohon Tunggu...");
        sweetAlertDialog.setCancelable(false);
        initData(gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class).getBilling());
        return view;
    }

    private void initData(final Billing billing){
        List<Profile> profiles  = new ArrayList<>();
        Profile profile         = new Profile();
        profile.setTitle("Status");
        profile.setValue(billing.getStatus());
        profiles.add(profile);

        Profile profile1        = new Profile();
        profile1.setTitle("TglNota");
        profile1.setValue(billing.getTglnota());
        profiles.add(profile1);

        Profile profile2        = new Profile();
        profile2.setTitle("Total");
        profile2.setValue(billing.getTotal());
        profiles.add(profile2);

        Profile profile3        = new Profile();
        profile3.setTitle("Kode Billing");
        profile3.setValue(billing.getKodeBilling());
        profiles.add(profile3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_billing.setLayoutManager(linearLayoutManager);
        rv_billing.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_billing.getContext(),
                linearLayoutManager.getOrientation());
        rv_billing.addItemDecoration(dividerItemDecoration);

        detailDataAdapter  = new DetailDataAdapter(getActivity(),profiles);
        rv_billing.setAdapter(detailDataAdapter);

        if (!TextUtils.isEmpty(billing.getLinkKuitansi()) || !TextUtils.isEmpty(billing.getLinkNota())){
            ll_button.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(billing.getLinkKuitansi())){
                btn_kuitansi.setVisibility(View.VISIBLE);
            } else {
                btn_kuitansi.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(billing.getLinkNota())){
                btn_nota.setVisibility(View.VISIBLE);
            } else {
                btn_nota.setVisibility(View.GONE);
            }

            btn_kuitansi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    down(billing.getLinkKuitansi(),"Kuitansi");
                }
            });

            btn_nota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    down(billing.getLinkNota(),"Nota");
                }
            });
        } else {
            ll_button.setVisibility(View.GONE);
        }
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
}
