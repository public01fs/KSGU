package com.application.ksgu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.ProfileListAdapter;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.R;

import java.util.List;

public class PerusahaanDetailFragment extends Fragment {

    View view;
    List<Profile> profileList;
    ProfileListAdapter profileListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view        = inflater.inflate(R.layout.fragment_perusahaan_detail, container, false);
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
    }
}
