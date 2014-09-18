package com.sarmaru.mihai.weatherapp.adapter;

import java.text.DecimalFormat;

import com.sarmaru.mihai.weatherapp.R;

public class WeatherObject {
	
	// Private variables
	private int _id;
	private String _location;
	private int _icon;
	private String _temperature;
	private String _description;
	private String _humidity;
	private String _pressure;
	private int _unit;
	private int _type; // today or tomorrow
	
	// Public TYPE members
	public static final int TODAY = 1;
	public static final int TOMORROW = 2;
	
	// Weather units
	public static final int DEFAULT = 0;
	public static final int METRIC = 1;
	public static final int IMPERIAL = 2;
	
	// Weather Icons
	public static final int WEATHER_SUNNY = R.string.weather_sunny;
	public static final int WEATHER_CLEAR_NIGHT = R.string.weather_clear_night;
	public static final int WEATHER_FOGGY = R.string.weather_foggy;
	public static final int WEATHER_CLOUDY = R.string.weather_cloudy;
	public static final int WEATHER_RAINY = R.string.weather_rainy;
	public static final int WEATHER_SNOWY = R.string.weather_snowy;
	public static final int WEATHER_THUNDER = R.string.weather_thunder;
	public static final int WEATHER_DRIZZLE = R.string.weather_drizzle;
	
	// Empty constructor
	public WeatherObject() {}
	
	// Full constructor
	public WeatherObject (int id, String location, int icon, String temperature,
			String description, String humidity, String pressure, int unit, int type) {
		this._id = id;
		this._location = location;
		this._icon = icon;
		this._temperature = temperature;
		this._description = description;
		this._humidity = humidity;
		this._pressure = pressure;
		this._unit = unit;
		this._type = type;
	}
	
	// Constructor (for inserting objects)
	public WeatherObject (String location, int icon, String temperature,
			String description, String humidity, String pressure, int unit, int type) {
		this._location = location;
		this._icon = icon;
		this._temperature = temperature;
		this._description = description;
		this._humidity = humidity;
		this._pressure = pressure;
		this._unit = unit;
		this._type = type;
	}
	
	// Getters and Setters
	public int getId() {
		return this._id;
	}
	public void setId (int id) {
		this._id = id;
	}
	
	public String getLocation() {
		return this._location;
	}
	public void setLocation(String location) {
		this._location = location;
	}
	
	public int getIcon() {
		return this._icon;
	}
	public void setIcon (int icon) {
		this._icon = icon;
	}
	
	public String getTemperature() {
		return this._temperature;
	}
	public void setTemperature (String temperature) {
		DecimalFormat df = new DecimalFormat("#.#");
		this._temperature = df.format(Double.parseDouble(temperature)).toString();
	}
	
	public String getDescription() {
		return this._description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	
	public String getHumidity() {
		return this._humidity;
	}
	public void setHumidity (String humidity) {
		this._humidity = humidity;
	}
	
	public String getPressure() {
		return this._pressure;
	}
	public void setPressure(String pressure) {
		this._pressure = pressure;
	}
	
	public int getUnit() {
		return this._unit;
	}
	public void setUnit(int unit) {
		this._unit = unit;
	}
	
	public int getType() {
		return this._type;
	}
	public void setType (int type) {
		this._type = type;
	}

}
