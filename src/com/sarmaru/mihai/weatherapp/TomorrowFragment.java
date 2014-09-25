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
	private static TextView tomorrowForecastOneTemp, tomorrowForecastTwoTemp, tomorrowForecastThreeTemp, tomorrowForecastFourTemp;
	private static TextView tomorrowForecastOneDay, tomorrowForecastTwoDay, tomorrowForecastThreeDay, tomorrowForecastFourDay;
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
		
		tomorrowForecastOneTemp = (TextView) rootView.findViewById(R.id.tomorrow_forecast1);
		tomorrowForecastTwoTemp = (TextView) rootView.findViewById(R.id.tomorrow_forecast2);
		tomorrowForecastThreeTemp = (TextView) rootView.findViewById(R.id.tomorrow_forecast3);
		tomorrowForecastFourTemp = (TextView) rootView.findViewById(R.id.tomorrow_forecast4);
		
		tomorrowForecastOneDay = (TextView) rootView.findViewById(R.id.tomorrow_forecast1_day);
		tomorrowForecastTwoDay = (TextView) rootView.findViewById(R.id.tomorrow_forecast2_day);
		tomorrowForecastThreeDay = (TextView) rootView.findViewById(R.id.tomorrow_forecast3_day);
		tomorrowForecastFourDay = (TextView) rootView.findViewById(R.id.tomorrow_forecast4_day);
	}
}
