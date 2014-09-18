package com.sarmaru.mihai.weatherapp.adapter;

public class Utils {
	
	// Format URL string (metric default)
	public static String formatUrlString(String url, String city) {
		return formatUrlString(url, city, WeatherObject.DEFAULT);
	}
	
	// Format URL string (unit choice)
	public static String formatUrlString(String url, String city, int unit) {
		String formatUrl = null;
		
		// Check for unit type
		if (unit == WeatherObject.METRIC || unit == WeatherObject.DEFAULT) {
			formatUrl = String.format(url, city) + "&units=metric";
		} else if (unit == WeatherObject.IMPERIAL) {
			formatUrl = String.format(url, city) + "&units=imperial";
		}
		
		// Return formated URL
		return formatUrl;
	}

}
