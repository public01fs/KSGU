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

import com.application.ksgu.Fragment.DashboardFragment;
import com.application.ksgu.Fragment.PermohonanListFragment;
import com.application.ksgu.Fragment.ProfileFragment;
import com.application.ksgu.Library.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    SessionManager sessionManager;
    HashMap<String, String> getLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigation    = findViewById(R.id.bottom_navigation);
        sessionManager      = new SessionManager(this);
        getLogin            = sessionManager.getLogin();

        if (!sessionManager.isLoggedIn()){
            Intent Jump_to_login = new Intent(this, LoginActivity.class);
            Jump_to_login.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Jump_to_login);
            finish();
            return;
        }

        custom(savedInstanceState);

    }

    private void custom(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Class fragmentClass = DashboardFragment.class;
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
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        Class fragmentClass;
                        item.setChecked(true);
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragmentClass = DashboardFragment.class;
                                break;
                            case R.id.action_profile:
                                fragmentClass = ProfileFragment.class;
                                break;
                            case R.id.action_permohonan:
                                fragmentClass = PermohonanListFragment.class;
                                break;
                            default:
                                fragmentClass = DashboardFragment.class;
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
}
