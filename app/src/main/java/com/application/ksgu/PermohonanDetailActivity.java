package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.application.ksgu.Adapter.PermohonanDetailAdapter;
import com.application.ksgu.Adapter.PermohonanListAdapter;
import com.application.ksgu.Fragment.BillingFragment;
import com.application.ksgu.Fragment.DetailFragment;
import com.application.ksgu.Fragment.DisposisiFragment;
import com.application.ksgu.Fragment.KapalFragment;
import com.application.ksgu.Fragment.LampiranFragment;
import com.application.ksgu.Fragment.PermohonanBaruFragment;
import com.application.ksgu.Fragment.PermohonanSelesaiFragment;
import com.application.ksgu.Model.DetailSuratNew;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class PermohonanDetailActivity extends AppCompatActivity {

    ViewPager vp_detail;
    TabLayout tabLayout;
    ImageView iv_back;
    SharedPreferences prefs;
    DetailSuratNew detail;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_detail);

        vp_detail   = findViewById(R.id.vp_detail);
        tabLayout   = findViewById(R.id.htab_tabs);
        iv_back     = findViewById(R.id.iv_back);
        prefs       = getSharedPreferences("detail", Context.MODE_PRIVATE);
        detail      = gson.fromJson(prefs.getString("detail",""), DetailSuratNew.class);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setupViewPager(vp_detail);
        tabLayout.setupWithViewPager(vp_detail);
    }

    private void setupViewPager(ViewPager viewPager) {
        PermohonanDetailAdapter adapter = new PermohonanDetailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new DetailFragment(),"Detail");
        adapter.addFrag(new DisposisiFragment(),"Disposisi");
        if (detail.getSuratListOne().getREQUIRECHECK().toLowerCase().equals("kapal")){
            adapter.addFrag(new KapalFragment(), "Kapal");
        }
        adapter.addFrag(new BillingFragment(),"Billing");
        adapter.addFrag(new LampiranFragment(), "Lampiran");
        viewPager.setAdapter(adapter);
    }
}
