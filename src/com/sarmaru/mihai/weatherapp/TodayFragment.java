package com.sarmaru.mihai.weatherapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate fragment XML
		View rootView = inflater.inflate(R.layout.fragment_today, container, false);
		
		// Set weatherFont to icon TextView
		TextView icon = (TextView) rootView.findViewById(R.id.today_icon);
		Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-font.ttf");
		icon.setTypeface(weatherFont);
		
		// Return view
		return rootView;
	}
}
