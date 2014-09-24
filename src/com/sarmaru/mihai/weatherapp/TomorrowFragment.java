package com.sarmaru.mihai.weatherapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TomorrowFragment extends Fragment {
	
	// Tomorrow Views
	private static TextView tomorrowName, tomorrowIcon, tomorrowTemperature, tomorrowDescription;
	private static TextView tomorrowForecastOne, tomorrowForecastTwo, tomorrowForecastThree, tomorrowForecastFour;
	private static TextView tomorrowForecastOneIcon, tomorrowForecastTwoIcon, tomorrowForecastThreeIcon, tomorrowForecastFourIcon;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate fragment XML
		View rootView = inflater.inflate(R.layout.fragment_tomorrow, container, false);
		
		// Initialize text views
		initializeViews(rootView);
		
		// Return view
		return rootView;
	}

	// Initialize text views
	private void initializeViews(View rootView) {
		tomorrowName = (TextView) rootView.findViewById(R.id.tomorrow_name);
		
		// Set weatherFont to icon TextView
		tomorrowIcon = (TextView) rootView.findViewById(R.id.tomorrow_icon);
		tomorrowForecastOneIcon = (TextView) rootView.findViewById(R.id.tomorrow_forecast1_icon);
		tomorrowForecastTwoIcon = (TextView) rootView.findViewById(R.id.tomorrow_forecast2_icon);
		tomorrowForecastThreeIcon = (TextView) rootView.findViewById(R.id.tomorrow_forecast3_icon);
		tomorrowForecastFourIcon = (TextView) rootView.findViewById(R.id.tomorrow_forecast4_icon);
		
		Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-font.ttf");
		tomorrowIcon.setTypeface(weatherFont);
		tomorrowForecastOneIcon.setTypeface(weatherFont);
		tomorrowForecastTwoIcon.setTypeface(weatherFont);
		tomorrowForecastThreeIcon.setTypeface(weatherFont);
		tomorrowForecastFourIcon.setTypeface(weatherFont);
		
		tomorrowTemperature = (TextView) rootView.findViewById(R.id.tomorrow_temperature);
		tomorrowDescription = (TextView) rootView.findViewById(R.id.tomorrow_description);
		
		tomorrowForecastOne = (TextView) rootView.findViewById(R.id.tomorrow_forecast1);
		tomorrowForecastTwo = (TextView) rootView.findViewById(R.id.tomorrow_forecast2);
		tomorrowForecastThree = (TextView) rootView.findViewById(R.id.tomorrow_forecast3);
		tomorrowForecastFour = (TextView) rootView.findViewById(R.id.tomorrow_forecast4);
	}
}
