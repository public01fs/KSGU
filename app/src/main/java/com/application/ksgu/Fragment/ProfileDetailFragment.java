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
import com.application.ksgu.Adapter.SimpleRecyclerAdapter;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.VersionModel;
import com.application.ksgu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileDetailFragment extends Fragment {

    View view;
    List<Profile> profileList = new ArrayList<>();
    ProfileListAdapter profileListAdapter;
    RecyclerView rv_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view        = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        rv_profile  = view.findViewById(R.id.rv_profile);

        initProfile();

        return view;
    }

    private void initProfile(){
        Profile profile = new Profile();
        profile.setTitle("Nama");
        profile.setValue("Trisna");
        profileList.add(profile);

        Profile profile1 = new Profile();
        profile1.setTitle("Email");
        profile1.setValue("trisnaagung555@gmail.com");
        profileList.add(profile1);

        Profile profile2 = new Profile();
        profile2.setTitle("Alamat");
        profile2.setValue("Surabaya");
        profileList.add(profile2);

        Profile profile3 = new Profile();
        profile3.setTitle("Kota");
        profile3.setValue("Surabaya");
        profileList.add(profile3);

        Profile profile4 = new Profile();
        profile4.setTitle("Telepon");
        profile4.setValue("081234567890");
        profileList.add(profile4);

        Profile profile5 = new Profile();
        profile5.setTitle("Deskripsi");
        profile5.setValue("Belum Ada");
        profileList.add(profile5);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_profile.setLayoutManager(linearLayoutManager);
        rv_profile.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_profile.getContext(),
                linearLayoutManager.getOrientation());
        rv_profile.addItemDecoration(dividerItemDecoration);

        profileListAdapter  = new ProfileListAdapter(getActivity(),profileList);
        rv_profile.setAdapter(profileListAdapter);
    }
}
