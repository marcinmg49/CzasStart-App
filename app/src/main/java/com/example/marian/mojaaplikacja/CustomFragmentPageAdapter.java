package com.example.marian.mojaaplikacja;

/**
 * Created by Marcin on 29.10.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class CustomFragmentPageAdapter extends FragmentPagerAdapter{



    private static final int FRAGMENT_COUNT = 2;

    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TreningActivity();
            case 1:
                return new MapsActivity();

        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Trening";
            case 1:
                return "Mapa";
        }
        return null;
    }
}
