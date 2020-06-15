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
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.SuratDetail;
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
        initData(gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class).getSuratDetail(),gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class).getSuratListOne());
        return view;
    }

    private void initData(SuratDetail suratDetail, SuratListOne suratListOne){
        List<Profile> profiles  = new ArrayList<>();
        Profile profile         = new Profile();
        profile.setTitle("Status");
        profile.setValue(suratDetail.getStatus());
        profiles.add(profile);

        Profile profile1        = new Profile();
        profile1.setTitle("Nomor");
        profile1.setValue(suratDetail.getNomor());
        profiles.add(profile1);

        Profile profile2        = new Profile();
        profile2.setTitle("Tanggal");
        profile2.setValue(suratDetail.getTanggal());
        profiles.add(profile2);

        Profile profile3        = new Profile();
        profile3.setTitle("Perihal");
        profile3.setValue(suratDetail.getPerihal());
        profiles.add(profile3);

        String[] pemohon = suratDetail.getPemohon().split("-");

        Profile profile4        = new Profile();
        profile4.setTitle("Pemohon");
        profile4.setValue(pemohon[0].replace(" ","")+"\n"+pemohon[1].replace(" ",""));
        profiles.add(profile4);

        String[] kontak = suratDetail.getKontak().split("-");

        Profile profile5        = new Profile();
        profile5.setTitle("Kontak");
        profile5.setValue(kontak[0].replace(" ","")+"\n"+kontak[1].replace(" ",""));
        profiles.add(profile5);

        Profile profile6        = new Profile();
        profile6.setTitle("Catatan");
        profile6.setValue(suratDetail.getCatatan());
        profiles.add(profile6);

        Profile profile7        = new Profile();
        profile7.setTitle("Create");
        profile7.setValue(suratListOne.getSURATINPUTTGL() +"\n("+suratListOne.getSURATINPUT()+")");
//        profile7.setValue(suratListOne.getCreated());
        profiles.add(profile7);

        Profile profile8        = new Profile();
        profile8.setTitle("Updated");
        profile8.setValue(suratListOne.getSURATEDITTGL() +"\n("+suratListOne.getSURATEDIT()+")");
//        profile8.setValue(suratListOne.getUpdated());
        profiles.add(profile8);

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
