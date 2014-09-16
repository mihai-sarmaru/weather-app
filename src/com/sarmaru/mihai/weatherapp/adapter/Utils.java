package com.sarmaru.mihai.weatherapp.adapter;

public class Utils {
	
	// Units
	public static final int METRIC = 1;
	public static final int IMPERIAL = 2;
	
	// Private default unit - metric
	private static int DEFAULT = 0;
	
	
	// Format URL string (metric default)
	public static String formatUrlString(String url, String city) {
		return formatUrlString(url, city, DEFAULT);
	}
	
	// Format URL string (unit choice)
	public static String formatUrlString(String url, String city, int unit) {
		String formatUrl = null;
		
		// Check for unit type
		if (unit == METRIC || unit == DEFAULT) {
			formatUrl = String.format(url, city) + "&units=metric";
		} else if (unit == IMPERIAL) {
			formatUrl = String.format(url, city) + "&units=imperial";
		}
		
		// Return formated URL
		return formatUrl;
	}

}
