package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.application.ksgu.Adapter.PermohonanDetailAdapter;
import com.application.ksgu.Adapter.PermohonanListAdapter;
import com.application.ksgu.Fragment.DetailFragment;
import com.application.ksgu.Fragment.KapalFragment;
import com.application.ksgu.Fragment.PermohonanBaruFragment;
import com.application.ksgu.Fragment.PermohonanSelesaiFragment;
import com.google.android.material.tabs.TabLayout;

public class PermohonanDetailActivity extends AppCompatActivity {

    ViewPager vp_detail;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_detail);

        vp_detail   = findViewById(R.id.vp_detail);
        tabLayout   = findViewById(R.id.htab_tabs);

        setupViewPager(vp_detail);
        tabLayout.setupWithViewPager(vp_detail);
    }

    private void setupViewPager(ViewPager viewPager) {
        PermohonanDetailAdapter adapter = new PermohonanDetailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new DetailFragment(),"Detail");
        adapter.addFrag(new PermohonanBaruFragment(), "Kembali");
        adapter.addFrag(new KapalFragment(), "Kapal");
        viewPager.setAdapter(adapter);
    }
}
