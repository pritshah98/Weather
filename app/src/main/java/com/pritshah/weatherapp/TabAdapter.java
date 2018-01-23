package com.pritshah.weatherapp;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by pritshah on 8/13/17.
 */

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
//            case 0:
//                return new WeatherFromLocationActivity();
//            case 1:
//                return new SearchFragment();
//            case 2:
//                return new ForecastActivity();
            case 0:
                return new SearchFragment();
            case 1:
                return new ForecastActivity();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
//            case 0:
//                return "CURRENT LOCATION";
//            case 1:
//                return "SEARCH WEATHER";
//            case 2:
//                return "WEATHER FORECAST";
            case 0:
                return "SEARCH WEATHER";
            case 1:
                return "WEATHER FORECAST";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
