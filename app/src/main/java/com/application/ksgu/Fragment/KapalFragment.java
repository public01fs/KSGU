package com.application.ksgu.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.DetailDataAdapter;
import com.application.ksgu.Model.DetailSurat;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.SuratListOne;
import com.application.ksgu.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class KapalFragment extends Fragment {

    View view;
    RecyclerView rv_kapal;
    SharedPreferences prefs;
    Gson gson = new Gson();
    DetailDataAdapter detailDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_kapal, container, false);
        rv_kapal        = view.findViewById(R.id.rv_kapal);
        prefs           = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        initData(gson.fromJson(prefs.getString("detail",""), DetailSurat.class).getSuratListOne());
        return view;
    }

    private void initData(SuratListOne suratListOne){
        List<Profile> profiles  = new ArrayList<>();
        Profile profile         = new Profile();
        profile.setTitle("Kapal");
        profile.setValue(suratListOne.getKAPALNAME());
        profiles.add(profile);

        Profile profile1        = new Profile();
        profile1.setTitle("GT");
        profile1.setValue(suratListOne.getKAPALGT());
        profiles.add(profile1);

        Profile profile2        = new Profile();
        profile2.setTitle("Call Sign");
        profile2.setValue(suratListOne.getKAPALCS());
        profiles.add(profile2);

        Profile profile3        = new Profile();
        profile3.setTitle("Posisi");
        profile3.setValue(suratListOne.getKAPALPOSISI());
        profiles.add(profile3);

        Profile profile4        = new Profile();
        profile4.setTitle("Pemilik");
        profile4.setValue(suratListOne.getKAPALPEMILIK());
        profiles.add(profile4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_kapal.setLayoutManager(linearLayoutManager);
        rv_kapal.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_kapal.getContext(),
                linearLayoutManager.getOrientation());
        rv_kapal.addItemDecoration(dividerItemDecoration);

        detailDataAdapter  = new DetailDataAdapter(getActivity(),profiles);
        rv_kapal.setAdapter(detailDataAdapter);
    }
}
