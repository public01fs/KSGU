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
import com.application.ksgu.Adapter.ProfileListAdapter;
import com.application.ksgu.Model.DetailSurat;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.SuratListOne;
import com.application.ksgu.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    View view;
    RecyclerView rv_detail;
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
        view            = inflater.inflate(R.layout.fragment_detail, container, false);
        rv_detail       = view.findViewById(R.id.rv_detail);
        prefs           = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        initData(gson.fromJson(prefs.getString("detail",""), DetailSurat.class).getSuratListOne());
        return view;
    }

    private void initData(SuratListOne suratListOne){
        List<Profile> profiles  = new ArrayList<>();
        Profile profile         = new Profile();
        profile.setTitle("Status");
        profile.setValue(suratListOne.getSTATUSNAME());
        profiles.add(profile);

        Profile profile1        = new Profile();
        profile1.setTitle("Nomor");
        profile1.setValue(suratListOne.getSURATNO());
        profiles.add(profile1);

        Profile profile2        = new Profile();
        profile2.setTitle("Tanggal");
        profile2.setValue(suratListOne.getSURATTGL());
        profiles.add(profile2);

        Profile profile3        = new Profile();
        profile3.setTitle("Perihal");
        profile3.setValue(suratListOne.getSURATHAL());
        profiles.add(profile3);

        Profile profile4        = new Profile();
        profile4.setTitle("Pemohon");
        profile4.setValue(suratListOne.getSURATPENGIRIM());
        profiles.add(profile4);

        Profile profile5        = new Profile();
        profile5.setTitle("Kontak");
        profile5.setValue(suratListOne.getSURATCP());
        profiles.add(profile5);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_detail.setLayoutManager(linearLayoutManager);
        rv_detail.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_detail.getContext(),
                linearLayoutManager.getOrientation());
        rv_detail.addItemDecoration(dividerItemDecoration);

        detailDataAdapter  = new DetailDataAdapter(getActivity(),profiles);
        rv_detail.setAdapter(detailDataAdapter);
    }
}
