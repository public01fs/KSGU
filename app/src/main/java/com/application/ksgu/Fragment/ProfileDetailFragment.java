package com.application.ksgu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.ProfileListAdapter;
import com.application.ksgu.Adapter.SimpleRecyclerAdapter;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.Model.VersionModel;
import com.application.ksgu.R;
import com.application.ksgu.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.application.ksgu.Cons.KEY_ALAMAT;
import static com.application.ksgu.Cons.KEY_DAERAH;
import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_KETERANGAN;
import static com.application.ksgu.Cons.KEY_NAME;
import static com.application.ksgu.Cons.KEY_TELEPON;

public class ProfileDetailFragment extends Fragment {

    View view;
    List<Profile> profileList = new ArrayList<>();
    ProfileListAdapter profileListAdapter;
    RecyclerView rv_profile;
    HashMap<String, String> getLogin;
    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        sessionManager  = new SessionManager(getContext());
        rv_profile  = view.findViewById(R.id.rv_profile);

        initProfile();

        return view;
    }

    private void initProfile(){
        getLogin    = sessionManager.getLogin();
        Profile profile = new Profile();
        profile.setTitle("Nama");
        profile.setValue(getLogin.get(KEY_NAME));
        profileList.add(profile);

        Profile profile1 = new Profile();
        profile1.setTitle("Email");
        profile1.setValue(getLogin.get(KEY_EMAIL));
        profileList.add(profile1);

        Profile profile2 = new Profile();
        profile2.setTitle("Alamat");
        profile2.setValue(getLogin.get(KEY_ALAMAT));
        profileList.add(profile2);

        Profile profile3 = new Profile();
        profile3.setTitle("Kota");
        profile3.setValue(getLogin.get(KEY_DAERAH));
        profileList.add(profile3);

        Profile profile4 = new Profile();
        profile4.setTitle("Telepon");
        profile4.setValue(getLogin.get(KEY_TELEPON));
        profileList.add(profile4);

        Profile profile5 = new Profile();
        profile5.setTitle("Deskripsi");
        profile5.setValue((TextUtils.isEmpty(getLogin.get(KEY_KETERANGAN))?"Belum Ada":getLogin.get(KEY_KETERANGAN)));
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
