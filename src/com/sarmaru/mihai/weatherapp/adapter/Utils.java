package com.sarmaru.mihai.weatherapp.adapter;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {
	
	// Format URL string (metric default)
	public static String formatUrlString(String url, String city) {
		return formatUrlString(url, city, WeatherObject.DEFAULT, WeatherObject.TODAY);
	}
	
	// Format URL string (unit choice)
	public static String formatUrlString(String url, String city, int unit, int type) {
		String formatUrl = null;
		
		// Check for unit type
		if (unit == WeatherObject.METRIC || unit == WeatherObject.DEFAULT) {
			formatUrl = String.format(url, city) + "&units=metric";
		} else if (unit == WeatherObject.IMPERIAL) {
			formatUrl = String.format(url, city) + "&units=imperial";
		}
		
		// Check for forecast type
		if (type == WeatherObject.TOMORROW) {
			formatUrl += "&cnt=6";
		}
		// Return formated URL
		return formatUrl;
	}
	
	// Check Internet connection
	public static boolean isNetworkAvailable(Context context) {
		// Return connectivity service active network info
	    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}

}
