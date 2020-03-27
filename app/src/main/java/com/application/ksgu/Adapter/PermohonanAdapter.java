package com.application.ksgu.Adapter;

import com.application.ksgu.Fragment.DataPemohonFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PermohonanAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PermohonanAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DataPemohonFragment tab1 = new DataPemohonFragment();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}