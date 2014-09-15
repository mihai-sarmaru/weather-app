package com.sarmaru.mihai.weatherapp.adapter;

import com.sarmaru.mihai.weatherapp.TodayFragment;
import com.sarmaru.mihai.weatherapp.TomorrowFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	// Constructor
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			// Today View Fragment activity
			return new TodayFragment();
		case 1:
			// Tomorrow View Fragment activity
			return new TomorrowFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// Tabs item count = number of tabs
		return 2;
	}

}
