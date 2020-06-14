package com.application.ksgu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.application.ksgu.Fragment.Dashboard2Fragment;
import com.application.ksgu.Fragment.DashboardFragment;
import com.application.ksgu.Fragment.PermohonanListFragment;
import com.application.ksgu.Fragment.ProfileFragment;
import com.application.ksgu.Library.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main3Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SessionManager sessionManager;
    Class fragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sessionManager          = new SessionManager(this);
        bottomNavigationView    = findViewById(R.id.bottom_navigation);

//        if (sessionManager.isLoggedIn()){
//            bottomNavigationView.getMenu().clear();
//            bottomNavigationView.inflateMenu(R.menu.menu_new);
//        } else {
//            bottomNavigationView.getMenu().clear();
//            bottomNavigationView.inflateMenu(R.menu.menu_login);
//        }

        custom(savedInstanceState);
    }

    private void custom(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Class fragmentClass = Dashboard2Fragment.class;
            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        item.setChecked(true);
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragmentClass = Dashboard2Fragment.class;
                                break;
                            case R.id.action_profile:
                                if (sessionManager.isLoggedIn()){
                                    fragmentClass = ProfileFragment.class;
                                } else {
                                    startActivity(new Intent(Main3Activity.this,LoginActivity2.class));
                                }
                                break;
                            default:
                                fragmentClass = Dashboard2Fragment.class;
                                break;
                        }
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sessionManager.isLoggedIn()){
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.menu_login);
        }
    }
}
