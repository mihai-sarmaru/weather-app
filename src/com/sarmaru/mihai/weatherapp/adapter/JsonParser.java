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
	public static WeatherObject parseWeatherJson(String jsonString) {
		try {
			weather = new WeatherObject();
			JSONObject json = new JSONObject(jsonString);

			// Location
			String location = json.getString(TAG_NAME) + ", " + json.getJSONObject(TAG_SYS).getString(TAG_COUNTRY);
			weather.setLocation(location);

			// Icon logic
			int iconCode = json.getJSONArray(TAG_WEATHER).getJSONObject(0).getInt(TAG_ICON);
			long sunrise = json.getJSONObject(TAG_SYS).getLong(TAG_SUNRISE) * 1000;
			long sunset = json.getJSONObject(TAG_SYS).getLong(TAG_SUNSET) * 1000;
			long currrentTime = new Date().getTime();

			if (iconCode == 800) {
				if (currrentTime >= sunrise && currrentTime < sunset) {
					weather.setIcon(WeatherObject.WEATHER_SUNNY);
				} else {
					weather.setIcon(WeatherObject.WEATHER_CLEAR_NIGHT);
				}
			}

			switch (iconCode / 100) {
			case 2:
				weather.setIcon(WeatherObject.WEATHER_THUNDER);
				break;
			case 3:
				weather.setIcon(WeatherObject.WEATHER_DRIZZLE);
				break;
			case 5:
				weather.setIcon(WeatherObject.WEATHER_RAINY);
				break;
			case 6:
				weather.setIcon(WeatherObject.WEATHER_SNOWY);
				break;
			case 7:
				weather.setIcon(WeatherObject.WEATHER_FOGGY);
				break;
			case 8:
				weather.setIcon(WeatherObject.WEATHER_CLOUDY);
				break;
			}

			// Details
			JSONObject main = json.getJSONObject(TAG_MAIN);
			weather.setTemperature(main.getString(TAG_TEMPERATURE));
			weather.setDescription(json.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
			weather.setHumidity(main.getString(TAG_HUMIDITY));
			weather.setPressure(main.getString(TAG_PRESSURE));

		} catch (Exception e) {
			// Log JSON parsing problems and print call stack
			Log.d("JSON", "There were problems parsing JSON");
			e.printStackTrace();
		}

		// Return weather object
		return weather;

	}
}
