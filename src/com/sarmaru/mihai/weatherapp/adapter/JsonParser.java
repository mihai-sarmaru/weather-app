package com.sarmaru.mihai.weatherapp.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
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
	
	private static final String TAG_PRECIPITATION = "rain";
	private static final String TAG_PRECIPITATION_VALUE = "3h";
	
	private static final String TAG_WIND = "wind";
	private static final String TAG_WIND_SPEED = "speed";

	private static final String TAG_WEATHER = "weather";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_ICON = "id"; // icon
	
	private static final String TAG_LIST = "list";
	private static final String TAG_CITY = "city";
	private static final String TAG_FORECAST_TEMPERATURE = "max";

	private static WeatherObject weather = null;

	// Parse weather JSON and return a weather object
	public static WeatherObject parseWeatherJson(String jsonString, int unit) {
		try {
			weather = new WeatherObject();
			JSONObject json = new JSONObject(jsonString);

			// Location
			String location = json.getString(TAG_NAME).isEmpty() ?
					json.getJSONObject(TAG_SYS).getString(TAG_COUNTRY) :
					json.getString(TAG_NAME) + ", " + json.getJSONObject(TAG_SYS).getString(TAG_COUNTRY);
			weather.setLocation(location);

			// Icon
			int iconCode = json.getJSONArray(TAG_WEATHER).getJSONObject(0).getInt(TAG_ICON);
			weather.setIcon(setWeatherIcon(json, iconCode, WeatherObject.TODAY));

			// Details
			JSONObject main = json.getJSONObject(TAG_MAIN);
			weather.setTemperature(main.getString(TAG_TEMPERATURE));
			weather.setDescription(json.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
			weather.setPrecipitation(getJsonPrecipitation(json));
			weather.setWind(json.getJSONObject(TAG_WIND).getString(TAG_WIND_SPEED));
			
			weather.setHumidity(main.getString(TAG_HUMIDITY));
			weather.setPressure(main.getString(TAG_PRESSURE));
			
			// Set unit and type
			weather.setUnit(unit);
			weather.setType(WeatherObject.TODAY);
			
			// Return weather object
			return weather;

		} catch (Exception e) {
			// Log JSON parsing problems and print call stack
			Log.d("JSON", "There were problems parsing JSON");
			return null;
		}
	}
	
	// Parse forecast JSON and return a weather object
	public static List<WeatherObject> parseForecastJson(String jsonString, int unit) {
		try {
			List<WeatherObject> forecastList = new ArrayList<WeatherObject>();
			JSONObject json = new JSONObject(jsonString);
			JSONArray wList = json.getJSONArray(TAG_LIST);
			
			// Iterate over forecast array. Skip first - current day
			for (int i = 1; i < wList.length(); i++) {
				WeatherObject weatherForecast = new WeatherObject();
				
				// Location
				String location = json.getJSONObject(TAG_CITY).getString(TAG_NAME).isEmpty() ?
						json.getJSONObject(TAG_CITY).getString(TAG_COUNTRY) :
						json.getJSONObject(TAG_CITY).getString(TAG_NAME) + ", " + json.getJSONObject(TAG_CITY).getString(TAG_COUNTRY);
				weatherForecast.setLocation(location);
				
				// Icon
				int iconCode = wList.getJSONObject(i).getJSONArray(TAG_WEATHER).getJSONObject(0).getInt(TAG_ICON);
				weatherForecast.setIcon(setWeatherIcon(json, iconCode, WeatherObject.TOMORROW));
				
				// Details
				weatherForecast.setTemperature(wList.getJSONObject(i).getJSONObject(TAG_TEMPERATURE).getString(TAG_FORECAST_TEMPERATURE));
				weatherForecast.setDescription(wList.getJSONObject(i).getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
				
				// Untracked forecast parameters
				weatherForecast.setPrecipitation("-");
				weatherForecast.setWind("-");
				weatherForecast.setHumidity("-");
				weatherForecast.setPressure("-");
				
				// Set unit and type
				weatherForecast.setUnit(unit);
				weatherForecast.setType(WeatherObject.TOMORROW);
				
				// Add weatherObject to list
				forecastList.add(weatherForecast);
			}

			// Return forecast list
			return forecastList;

		} catch (Exception e) {
			// Log JSON parsing problems and print call stack
			Log.d("JSON", "There were problems parsing JSON");
			return null;
		}
	}
	
	// Precipitation handling method
	private static String getJsonPrecipitation(JSONObject json) {
		String precipitation = null;
		
		// Try if object exists or catch and set null
		try {
			precipitation = json.getJSONObject(TAG_PRECIPITATION).getString(TAG_PRECIPITATION_VALUE);
		} catch (Exception e) {
			precipitation = "-";
		}
		
		// Return precipitation
		return precipitation;
	}
	
	// Icon setup based on JSON icon code
	private static int setWeatherIcon(JSONObject json, int iconCode, int type) {
		// Default weather icon
		int weatherIcon = WeatherObject.WEATHER_SUNNY;
		
		try {	
			long sunrise = 0;
			long sunset = 0;
			long currrentTime = new Date().getTime();;
			
			// Icon logic
			if (type == WeatherObject.TODAY) {
				sunrise = json.getJSONObject(TAG_SYS).getLong(TAG_SUNRISE) * 1000;
				sunset = json.getJSONObject(TAG_SYS).getLong(TAG_SUNSET) * 1000;
			}
			
			if (iconCode == 800 && type == WeatherObject.TODAY) {
				if (currrentTime >= sunrise && currrentTime < sunset) {
					weatherIcon = WeatherObject.WEATHER_SUNNY;
				} else {
					weatherIcon = WeatherObject.WEATHER_CLEAR_NIGHT;
				}
			} else if (iconCode == 800 && type == WeatherObject.TOMORROW) {
				weatherIcon = WeatherObject.WEATHER_SUNNY;
			} else {
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
