package com.application.ksgu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.ProfileListAdapter;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.R;
import com.application.ksgu.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerusahaanDetailFragment extends Fragment {

    View view;
    List<Profile> profileList = new ArrayList<>();
    ProfileListAdapter profileListAdapter;
    RecyclerView rv_perusahaan;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_perusahaan_detail, container, false);
        sessionManager  = new SessionManager(getContext());
        rv_perusahaan   = view.findViewById(R.id.rv_perusahaan);

        initProfile();

        return view;
    }

    private void initProfile(){
        getLogin    = sessionManager.getLogin();
        Profile profile = new Profile();
        profile.setTitle("Perusahaan");
        profile.setValue("");
        profileList.add(profile);

        Profile profile1 = new Profile();
        profile1.setTitle("Alamat");
        profile1.setValue("");
        profileList.add(profile1);

        Profile profile2 = new Profile();
        profile2.setTitle("Kota");
        profile2.setValue("");
        profileList.add(profile2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_perusahaan.setLayoutManager(linearLayoutManager);
        rv_perusahaan.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_perusahaan.getContext(),
                linearLayoutManager.getOrientation());
        rv_perusahaan.addItemDecoration(dividerItemDecoration);

        profileListAdapter  = new ProfileListAdapter(getActivity(),profileList);
        rv_perusahaan.setAdapter(profileListAdapter);
    }
}
