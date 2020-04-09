package com.application.ksgu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.ProfileAdapter;
import com.application.ksgu.Adapter.SimpleRecyclerAdapter;
import com.application.ksgu.Fragment.PerusahaanDetailFragment;
import com.application.ksgu.Fragment.ProfileDetailFragment;
import com.application.ksgu.Model.VersionModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    AppBarLayout appBarLayout;
    ImageView iv_back;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        appBarLayout        = findViewById(R.id.htab_appbar);
        iv_back             = findViewById(R.id.iv_back);
        tv_title            = findViewById(R.id.tv_title);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                if (i == 0) {
//                    iv_back.setVisibility(View.GONE);
//                    tv_title.setVisibility(View.GONE);
//                } else {
//                    iv_back.setVisibility(View.VISIBLE);
//                    tv_title.setVisibility(View.VISIBLE);
//                }

                if (i == 0) {
//                    if (onStateChangeListener != null && state != State.EXPANDED) {
//                        onStateChangeListener.onStateChange(State.EXPANDED);
//                    }
//                    state = State.EXPANDED;
                    iv_back.setVisibility(View.GONE);
                    tv_title.setVisibility(View.GONE);
                    Log.d("status","expand");
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
//                    if (onStateChangeListener != null && state != State.COLLAPSED) {
//                        onStateChangeListener.onStateChange(State.COLLAPSED);
//                    }
//                    state = State.COLLAPSED;
                    iv_back.setVisibility(View.VISIBLE);
                    tv_title.setVisibility(View.VISIBLE);
                    Log.d("status","collapse");
                } else {
//                    if (onStateChangeListener != null && state != State.IDLE) {
//                        onStateChangeListener.onStateChange(State.IDLE);
//                    }
//                    state = State.IDLE;
                    iv_back.setVisibility(View.GONE);
                    tv_title.setVisibility(View.GONE);
                    Log.d("status","idle");
                }
            }
        });

        final Toolbar toolbar = findViewById(R.id.htab_toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Parallax Tabs");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfileAdapter adapter = new ProfileAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new ProfileDetailFragment(),"Profile");
        adapter.addFrag(new PerusahaanDetailFragment(), "Perusahaan / Agen");
        viewPager.setAdapter(adapter);
    }

    public static class DummyFragment extends Fragment {
        int color;

        public DummyFragment() {
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);

            final FrameLayout frameLayout = view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            RecyclerView recyclerView = view.findViewById(R.id.dummyfrag_scrollableview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            List<String> list = Arrays.asList(VersionModel.data);

            SimpleRecyclerAdapter adapter = new SimpleRecyclerAdapter(list);
            recyclerView.setAdapter(adapter);

            return view;
        }
    }
}
