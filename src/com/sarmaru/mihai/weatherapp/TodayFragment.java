package com.sarmaru.mihai.weatherapp;

import java.util.Locale;

import com.sarmaru.mihai.weatherapp.adapter.WeatherObject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TodayFragment extends Fragment {
	
	// Today Views
	private static TextView todayName, todayIcon, todayTemperature, todayDescription, todayPrecipitation, todayWind, todayHumidity, todayPressure;
	private static TextView todayPrecipitationIcon, todayWindIcon, todayHumidityIcon, todayPressureIcon;
	private static TextView todayCopyright;
	
	private static RelativeLayout todayMain, todayNoLayout;

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
		// Relative layouts
		todayMain = (RelativeLayout) rootView.findViewById(R.id.today_main_layout);
		todayNoLayout = (RelativeLayout) rootView.findViewById(R.id.today_no_layout);
		
		// Hide main layout and show no weather layout
		todayMain.setVisibility(View.GONE);
		todayNoLayout.setVisibility(View.VISIBLE);
		
		todayName = (TextView) rootView.findViewById(R.id.today_name);
		
		// Set weatherFont to icon TextView
		todayIcon = (TextView) rootView.findViewById(R.id.today_icon);
		Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-font.ttf");
		todayIcon.setTypeface(weatherFont);
		
		// Set no weather icon
		TextView todayNoWeatherIcon = (TextView) rootView.findViewById(R.id.today_no_weather_icon);
		todayNoWeatherIcon.setTypeface(weatherFont);
		
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
		
		todayCopyright = (TextView) rootView.findViewById(R.id.today_copyright);
	}
	
	// Display weather object to text views
	public static void displayTodayWeather(Context context, WeatherObject weather) {
		// Set today main layout
		todayMain.setVisibility(View.VISIBLE);
		todayNoLayout.setVisibility(View.GONE);
		
		// Name and icon
		todayName.setText(weather.getLocation());
		todayIcon.setText(context.getString(weather.getIcon()));
		
		// Temperature, pressure and wind in specific units
		displayUnitSystemViews(context, weather);
		
		// Details
		todayDescription.setText(weather.getDescription().toLowerCase(Locale.US));
		if (weather.getPrecipitation() == "-") {
			todayPrecipitation.setText(weather.getPrecipitation());
		} else {
			todayPrecipitation.setText(weather.getPrecipitation() + " " + context.getString(R.string.millimeter));
		}
		
		todayHumidity.setText(weather.getHumidity() + " " + context.getString(R.string.percent));		
		todayCopyright.setText(context.getString(R.string.copyright));
	}
	
	// Notify user that weather info is outdated 
	public static void displayOutdatedInfo(Context context) {
		todayCopyright.setText(context.getString(R.string.outdated_weather));
	}
	
	private static void displayUnitSystemViews(Context context, WeatherObject weather) {
		if (weather.getUnit() == WeatherObject.METRIC || weather.getUnit() == WeatherObject.DEFAULT) {
			todayTemperature.setText(weather.getTemperature() + " " + context.getString(R.string.celsius));
			todayWind.setText(weather.getWind() + " " + context.getString(R.string.speed));
			// Convert Pascal to mmHG
			String metricPressure = (String.valueOf((int)(Integer.parseInt(weather.getPressure()) / 1.33)));
			todayPressure.setText(metricPressure + " " + context.getString(R.string.mercury));
		} else if (weather.getUnit() == WeatherObject.IMPERIAL) {
			todayTemperature.setText(weather.getTemperature() + " " + context.getString(R.string.fahrenheit));
			todayWind.setText(weather.getWind() + " " + context.getString(R.string.speed_imperial));
			todayPressure.setText(weather.getPressure() + " " + context.getString(R.string.pascal));
		}
		
	}
}


