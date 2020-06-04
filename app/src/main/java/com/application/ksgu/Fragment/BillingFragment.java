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
import com.application.ksgu.Model.Billing;
import com.application.ksgu.Model.DetailSuratNew;
import com.application.ksgu.Model.Profile;
import com.application.ksgu.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BillingFragment extends Fragment {

    View view;
    RecyclerView rv_billing;
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
        view        = inflater.inflate(R.layout.fragment_billing, container, false);
        rv_billing  = view.findViewById(R.id.rv_billing);
        prefs       = getActivity().getSharedPreferences("detail", Context.MODE_PRIVATE);
        initData(gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class).getBilling());
        return view;
    }

    private void initData(Billing billing){
        List<Profile> profiles  = new ArrayList<>();
        Profile profile         = new Profile();
        profile.setTitle("Status");
        profile.setValue(billing.getStatus());
        profiles.add(profile);

        Profile profile1        = new Profile();
        profile1.setTitle("TglNota");
        profile1.setValue(billing.getTglnota());
        profiles.add(profile1);

        Profile profile2        = new Profile();
        profile2.setTitle("Total");
        profile2.setValue(billing.getTotal());
        profiles.add(profile2);

        Profile profile3        = new Profile();
        profile3.setTitle("Kode Billing");
        profile3.setValue(billing.getKodeBilling());
        profiles.add(profile3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rv_billing.setLayoutManager(linearLayoutManager);
        rv_billing.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_billing.getContext(),
                linearLayoutManager.getOrientation());
        rv_billing.addItemDecoration(dividerItemDecoration);

        detailDataAdapter  = new DetailDataAdapter(getActivity(),profiles);
        rv_billing.setAdapter(detailDataAdapter);
    }
}
