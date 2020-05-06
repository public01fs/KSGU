package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.application.ksgu.Adapter.DetailAdapter;
import com.application.ksgu.Adapter.PermohonanListAdapter;
import com.application.ksgu.Fragment.PermohonanBaruFragment;
import com.application.ksgu.Fragment.PermohonanSelesaiFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailAdapter adapter = new DetailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new PermohonanBaruFragment(),"Baru");
        adapter.addFrag(new PermohonanBaruFragment(), "Kembali");
        adapter.addFrag(new PermohonanSelesaiFragment(), "Selesai");
        viewPager.setAdapter(adapter);
    }
}
