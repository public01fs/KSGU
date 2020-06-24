package com.application.ksgu.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.ksgu.Adapter.LampiranAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.SuratLampiranItem;
import com.application.ksgu.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class LampiranFragment extends Fragment {

    View view;
    RecyclerView rv_lampiran;
    SharedPreferences prefs;
    Gson gson = new Gson();
    List<SuratLampiranItem> suratLampiranItems = new ArrayList<>();
    LampiranAdapter lampiranAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view        = inflater.inflate(R.layout.fragment_lampiran, container, false);
        rv_lampiran = view.findViewById(R.id.rv_lampiran);
        prefs       = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
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
//                        down(dataPemasanganBaru.getUri_rek_listrik(),"Rekening Listrik");
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
}
