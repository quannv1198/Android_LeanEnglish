package com.example.quan.english.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new VideoFragment();
                break;
            case 2:
                fragment = new AudioFragment();
                break;
            case 3:
                fragment = new MenuFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
    }
