package com.application.ksgu.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.DetailDataAdapter;
import com.application.ksgu.Adapter.DisposisiListAdapter;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Disposisi;
import com.application.ksgu.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisposisiFragment extends Fragment {

    View view;
    RecyclerView rv_disposisi;
    SharedPreferences prefs;
    Gson gson = new Gson();
    DisposisiListAdapter disposisiListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_disposisi, container, false);
        rv_disposisi    = view.findViewById(R.id.rv_disposisi);
        prefs           = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        loadData(prefs.getString("detail",""));
        return view;
    }

    private void loadData(String data){
        List<Disposisi> disposisiList   = gson.fromJson(data,DetailSuratNew.class).getDisposisi();

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rv_disposisi.setLayoutManager(linearLayoutManager);
        rv_disposisi.setHasFixedSize(true);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_disposisi.getContext(),
//                0);
//        rv_disposisi.addItemDecoration(dividerItemDecoration);

        disposisiListAdapter            = new DisposisiListAdapter(getActivity(),disposisiList);
        rv_disposisi.setAdapter(disposisiListAdapter);
    }
}
