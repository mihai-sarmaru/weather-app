package com.sarmaru.mihai.weatherapp;

import com.sarmaru.mihai.weatherapp.adapter.WeatherObject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayFragment extends Fragment {
	
	// Today Views
	private static TextView todayName, todayIcon, todayTemperature, todayDescription, todayPrecipitation, todayWind, todayHumidity, todayPressure;
	private static TextView todayPrecipitationIcon, todayWindIcon, todayHumidityIcon, todayPressureIcon;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate fragment XML
		View rootView = inflater.inflate(R.layout.fragment_today, container, false);
		
		// Initialize text views
		initializeViews(rootView);
		
		// Return view
		return rootView;
	}
	
	// Initialize text views
	private void initializeViews(View rootView) {
		todayName = (TextView) rootView.findViewById(R.id.today_name);
		
		// Set weatherFont to icon TextView
		todayIcon = (TextView) rootView.findViewById(R.id.today_icon);
		Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-font.ttf");
		todayIcon.setTypeface(weatherFont);
		
		// Set details Icons
		todayPrecipitationIcon = (TextView) rootView.findViewById(R.id.today_precipitation_icon);
		todayWindIcon = (TextView) rootView.findViewById(R.id.today_wind_icon);
		todayHumidityIcon = (TextView) rootView.findViewById(R.id.today_humidity_icon);
		todayPressureIcon = (TextView) rootView.findViewById(R.id.today_pressure_icon);
		todayPrecipitationIcon.setTypeface(weatherFont);
		todayWindIcon.setTypeface(weatherFont);
		todayHumidityIcon.setTypeface(weatherFont);
		todayPressureIcon.setTypeface(weatherFont);
		
		todayTemperature = (TextView) rootView.findViewById(R.id.today_temperature);
		todayDescription = (TextView) rootView.findViewById(R.id.today_description);
		todayPrecipitation = (TextView) rootView.findViewById(R.id.today_precipitation);
		todayWind = (TextView) rootView.findViewById(R.id.today_wind);
		todayHumidity = (TextView) rootView.findViewById(R.id.today_humidity);
		todayPressure = (TextView) rootView.findViewById(R.id.today_pressure);
	}
	
	// Display weather object to text views
	public static void displayTodayWeather(Context context, WeatherObject weather) {
		// Name and icon
		todayName.setText(weather.getLocation());
		todayIcon.setText(context.getString(weather.getIcon()));
		
		// Temperature units
		if (weather.getUnit() == WeatherObject.METRIC || weather.getUnit() == WeatherObject.DEFAULT) {
			todayTemperature.setText(weather.getTemperature() + " " + context.getString(R.string.celsius));
		} else if (weather.getUnit() == WeatherObject.IMPERIAL) {
			todayTemperature.setText(weather.getTemperature() + " " + context.getString(R.string.fahrenheit));
		}
		
		// Details
		todayDescription.setText(weather.getDescription());
		todayHumidity.setText(weather.getHumidity() + " " + context.getString(R.string.percent));
		todayPressure.setText(weather.getPressure() + " " + context.getString(R.string.mercury));
	}
}


