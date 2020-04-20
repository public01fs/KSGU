package com.application.ksgu.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ksgu.Adapter.ProfileAdapter;
import com.application.ksgu.EditProfileActivity;
import com.application.ksgu.ProfileActivity;
import com.application.ksgu.R;
import com.application.ksgu.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_NAME;

public class ProfileFragment extends Fragment {

    View view;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view                = inflater.inflate(R.layout.fragment_profile, container, false);
        appBarLayout        = view.findViewById(R.id.htab_appbar);
        iv_back             = view.findViewById(R.id.iv_back);
        iv_photo            = view.findViewById(R.id.iv_photo);
        tv_title            = view.findViewById(R.id.tv_title);
        tv_nama             = view.findViewById(R.id.tv_nama);
        tv_email            = view.findViewById(R.id.tv_email);
        pb_loading          = view.findViewById(R.id.pbLoading);
//        btn_edit            = findViewById(R.id.btn_edit);
//        btn_logout          = findViewById(R.id.btn_logout);
        sessionManager      = new SessionManager(getContext());
        getLogin            = sessionManager.getLogin();
        toolbar             = view.findViewById(R.id.htab_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nama.setText(getLogin.get(KEY_NAME));
        tv_email.setText(getLogin.get(KEY_EMAIL));

        if (getLogin.get(KEY_IMG) != null) {
            Glide.with(getContext())
                    .load(getLogin.get(KEY_IMG))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
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
            Glide.with(getContext())
                    .load(R.drawable.ic_empty)
                    .fitCenter()
                    .into(iv_photo);
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i == 0) {
                    tv_title.setVisibility(View.GONE);
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    tv_title.setVisibility(View.VISIBLE);
                } else {
                    tv_title.setVisibility(View.GONE);
                }
            }
        });
        final ViewPager viewPager = view.findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = view.findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.htab_collapse_toolbar);

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
                getActivity().finish();
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfileAdapter adapter = new ProfileAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new ProfileDetailFragment(),"Profile");
        adapter.addFrag(new PerusahaanDetailFragment(), "Perusahaan / Agen");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
