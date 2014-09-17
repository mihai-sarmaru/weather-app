package com.sarmaru.mihai.weatherapp.adapter;

import java.util.Date;

import org.json.JSONObject;

import android.util.Log;

public class JsonParser {

	// JSON tags
	private static final String TAG_NAME = "name";
	private static final String TAG_SYS = "sys";
	private static final String TAG_COUNTRY = "country";
	private static final String TAG_SUNRISE = "sunrise";
	private static final String TAG_SUNSET = "sunset";

	private static final String TAG_MAIN = "main";
	private static final String TAG_TEMPERATURE = "temp";
	private static final String TAG_HUMIDITY = "humidity";
	private static final String TAG_PRESSURE = "pressure";

	private static final String TAG_WEATHER = "weather";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_ICON = "id"; // icon

	private static WeatherObject weather = null;

	// Parse weather JSON and return a weather object
	public static WeatherObject parseWeatherJson(String jsonString, int unit, int type) {
		try {
			weather = new WeatherObject();
			JSONObject json = new JSONObject(jsonString);

			// Location
			String location = json.getString(TAG_NAME) + ", " + json.getJSONObject(TAG_SYS).getString(TAG_COUNTRY);
			weather.setLocation(location);

			// Icon
			weather.setIcon(setWeatherIcon(json));

			// Details
			JSONObject main = json.getJSONObject(TAG_MAIN);
			weather.setTemperature(main.getString(TAG_TEMPERATURE));
			weather.setDescription(json.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
			weather.setHumidity(main.getString(TAG_HUMIDITY));
			weather.setPressure(main.getString(TAG_PRESSURE));
			
			// Set unit and type
			weather.setUnit(unit);
			weather.setType(type);

		} catch (Exception e) {
			// Log JSON parsing problems and print call stack
			Log.d("JSON", "There were problems parsing JSON");
			e.printStackTrace();
		}

		// Return weather object
		return weather;
	}
	
	// Icon setup based on JSON icon code
	private static int setWeatherIcon(JSONObject json) {
		// Default weather icon
		int weatherIcon = WeatherObject.WEATHER_SUNNY;
		
		try {	
			// Icon logic
			int iconCode = json.getJSONArray(TAG_WEATHER).getJSONObject(0).getInt(TAG_ICON);
			long sunrise = json.getJSONObject(TAG_SYS).getLong(TAG_SUNRISE) * 1000;
			long sunset = json.getJSONObject(TAG_SYS).getLong(TAG_SUNSET) * 1000;
			long currrentTime = new Date().getTime();

			if (iconCode == 800) {
				if (currrentTime >= sunrise && currrentTime < sunset) {
					weatherIcon = WeatherObject.WEATHER_SUNNY;
				} else {
					weatherIcon = WeatherObject.WEATHER_CLEAR_NIGHT;
				}
			}

			switch (iconCode / 100) {
			case 2:
				weatherIcon = WeatherObject.WEATHER_THUNDER;
				break;
			case 3:
				weatherIcon = WeatherObject.WEATHER_DRIZZLE;
				break;
			case 5:
				weatherIcon = WeatherObject.WEATHER_RAINY;
				break;
			case 6:
				weatherIcon = WeatherObject.WEATHER_SNOWY;
				break;
			case 7:
				weatherIcon = WeatherObject.WEATHER_FOGGY;
				break;
			case 8:
				weatherIcon = WeatherObject.WEATHER_CLOUDY;
				break;
			}
		} catch (Exception e) {
			// Log JSON parsing problems and print call stack
			Log.d("JSON", "There were problems in icon logic while parsing JSON");
			e.printStackTrace();
		}
		
		// Return weather icon
		return weatherIcon;
	}
}
