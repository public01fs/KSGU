package com.application.ksgu;

import androidx.appcompat.app.AlertDialog;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.ProfileAdapter;
import com.application.ksgu.Adapter.SimpleRecyclerAdapter;
import com.application.ksgu.Fragment.PerusahaanDetailFragment;
import com.application.ksgu.Fragment.ProfileDetailFragment;
import com.application.ksgu.Model.VersionModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_NAME;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    AppBarLayout appBarLayout;
    ImageView iv_back,iv_photo;
    TextView tv_title,tv_nama,tv_email;
    Button btn_edit,btn_logout;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;
    Toolbar toolbar;
    ProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        appBarLayout        = findViewById(R.id.htab_appbar);
        iv_back             = findViewById(R.id.iv_back);
        iv_photo            = findViewById(R.id.iv_photo);
        tv_title            = findViewById(R.id.tv_title);
        tv_nama             = findViewById(R.id.tv_nama);
        tv_email            = findViewById(R.id.tv_email);
        pb_loading          = findViewById(R.id.pbLoading);
//        btn_edit            = findViewById(R.id.btn_edit);
//        btn_logout          = findViewById(R.id.btn_logout);
        sessionManager      = new SessionManager(this);
        getLogin            = sessionManager.getLogin();
        toolbar             = findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nama.setText(getLogin.get(KEY_NAME));
        tv_email.setText(getLogin.get(KEY_EMAIL));

        if (getLogin.get(KEY_IMG) != null) {
            Glide.with(this)
                    .load(getLogin.get(KEY_IMG))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(iv_photo);
        } else {
            pb_loading.setVisibility(View.GONE);
            Glide.with(this)
                    .load(R.drawable.ic_empty)
                    .fitCenter()
                    .into(iv_photo);
        }

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
//                    iv_back.setVisibility(View.GONE);
                    tv_title.setVisibility(View.GONE);
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
//                    if (onStateChangeListener != null && state != State.COLLAPSED) {
//                        onStateChangeListener.onStateChange(State.COLLAPSED);
//                    }
//                    state = State.COLLAPSED;
//                    iv_back.setVisibility(View.VISIBLE);
                    tv_title.setVisibility(View.VISIBLE);
                } else {
//                    if (onStateChangeListener != null && state != State.IDLE) {
//                        onStateChangeListener.onStateChange(State.IDLE);
//                    }
//                    state = State.IDLE;
//                    iv_back.setVisibility(View.GONE);
                    tv_title.setVisibility(View.GONE);
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

//        btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
//            }
//        });

//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
//                builder.setMessage("Apakah anda yakin ingin keluar?")
//                        .setCancelable(false)
//                        .setPositiveButton("Iya",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        sessionManager.logoutUser();
////                                        startActivity(new Intent(getContext(), LoginActivity.class));
////                                        getActivity().finish();
////                                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                                    }
//                                })
//                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        }).show();
//            }
//        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
                break;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Apakah anda yakin ingin keluar?")
                        .setCancelable(false)
                        .setPositiveButton("Iya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        sessionManager.logoutUser();
                                    }
                                })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
