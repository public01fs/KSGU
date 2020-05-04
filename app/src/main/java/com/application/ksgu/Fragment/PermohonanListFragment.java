package com.application.ksgu.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.ksgu.Adapter.PermohonanListAdapter;
import com.application.ksgu.Adapter.ProfileAdapter;
import com.application.ksgu.R;
import com.google.android.material.tabs.TabLayout;

public class PermohonanListFragment extends Fragment {

    View view;
    ViewPager vp_content;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view        = inflater.inflate(R.layout.fragment_permohonan_list, container, false);
        vp_content  = view.findViewById(R.id.vp_permohonan);
        tabLayout   = view.findViewById(R.id.htab_tabs);

        setupViewPager(vp_content);
        tabLayout.setupWithViewPager(vp_content);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        PermohonanListAdapter adapter = new PermohonanListAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new PermohonanBaruFragment(),"Baru");
        adapter.addFrag(new PermohonanBaruFragment(), "Kembali");
        adapter.addFrag(new PermohonanSelesaiFragment(), "Selesai");
        viewPager.setAdapter(adapter);
    }
}
