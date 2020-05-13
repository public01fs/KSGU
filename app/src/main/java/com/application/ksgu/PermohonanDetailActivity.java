package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.application.ksgu.Adapter.PermohonanDetailAdapter;
import com.application.ksgu.Adapter.PermohonanListAdapter;
import com.application.ksgu.Fragment.DetailFragment;
import com.application.ksgu.Fragment.PermohonanBaruFragment;
import com.application.ksgu.Fragment.PermohonanSelesaiFragment;

public class PermohonanDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_detail);
    }

    private void setupViewPager(ViewPager viewPager) {
        PermohonanDetailAdapter adapter = new PermohonanDetailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new DetailFragment(),"Detail");
        adapter.addFrag(new PermohonanBaruFragment(), "Kembali");
        adapter.addFrag(new PermohonanSelesaiFragment(), "Selesai");
        viewPager.setAdapter(adapter);
    }
}
