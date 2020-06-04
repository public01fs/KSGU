package com.application.ksgu.Fragment;

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

import com.application.ksgu.Adapter.LampiranAdapter;
import com.application.ksgu.Adapter.UploadFotoAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.SuratLampiranItem;
import com.application.ksgu.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    }
}
