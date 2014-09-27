package com.sarmaru.mihai.weatherapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// Database version and version
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "weatherdb";
	
	// Table name
	private static final String TABLE_NAME = "weathertable";
	
	// Column names
	private static final String KEY_ID = "id";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_ICON = "icon";
	private static final String KEY_TEMPERATURE = "temperature";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_PRECIPITATION = "precipitation";
	private static final String KEY_WIND = "wind";
	private static final String KEY_HUMIDITY = "humidity";
	private static final String KEY_PRESSURE = "pressure";
	private static final String KEY_UNIT = "unit";
	private static final String KEY_TYPE = "type";
	
	// Constructor which calls superclass
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create table
		String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
				KEY_ID + " INTEGER PRIMARY KEY, " +
				KEY_LOCATION + " TEXT, " +
				KEY_ICON + " INTEGER, " +
				KEY_TEMPERATURE + " TEXT, " +
				KEY_DESCRIPTION + " TEXT, " +
				KEY_PRECIPITATION + " TEXT, " +
				KEY_WIND + " TEXT, " +
				KEY_HUMIDITY + " TEXT, " +
				KEY_PRESSURE + " TEXT, " +
				KEY_UNIT + " INTEGER, " +
				KEY_TYPE + " INTEGER)";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop current table and recreate database
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	// Update today WeatherObject
	public void updateTodayWeatherObject(WeatherObject weather) {
		// Get a writable database and update content values
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TABLE_NAME, convertWeatherToContentValues(weather),
				KEY_TYPE + " =?", new String[] {String.valueOf(weather.getType())});
	}
	
	// Update tomorrow WeatherObject
	public void updateTomorrowWeatherObject(List<WeatherObject> weatherList) {
		// Get a writable database and update content values
		SQLiteDatabase db = this.getWritableDatabase();
		// Get list of tomorrow id's
		List<Integer> idList = getTomorrowWeatherIDs(); 
		
		// Loop through list and update weatherObjects 
		for (int i = 0; i < weatherList.size(); i++) {
			db.update(TABLE_NAME, convertWeatherToContentValues(weatherList.get(i)),
					KEY_ID + " =?", new String[] {String.valueOf(idList.get(i))});
		}
	}
	
	// Insert today weatherObject into database
	public void insertTodayWeatherObject(WeatherObject weather) {
		// Get a writable database and insert content values
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_NAME, null, convertWeatherToContentValues(weather));
	}
	
	// Insert tomorrow weatherObjects into database
	public void insertTomorrowWeatherObjects(List<WeatherObject> weatherList) {
		// Get a writable database
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Loop each object list and insert into database
		for (WeatherObject weather : weatherList) {
			db.insert(TABLE_NAME, null, convertWeatherToContentValues(weather));
		}
	}
	
	// Read today's weatherObject from database
	public WeatherObject getTodayWeatherObject() {
		// Get a readable database, execute query and return data into a cursor object
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TYPE + " = " + WeatherObject.TODAY, null);
		
		// Check if cursor is not null
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		// Return weather object from cursor
		return getWeatherFromCursor(cursor);
	}
	
	// Read all tomorrow weatherObjects from database
	public List<WeatherObject> getTomorrowWeatherObjects() {
		// Get a readable database, execute query and return data into a cursor object
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TYPE + " = " + WeatherObject.TOMORROW, null);
		
		// Loop each cursor object and add info to list
		List<WeatherObject> weatherList = new ArrayList<WeatherObject>();
		if (cursor.moveToFirst()) {
			do {
				// Get weather from cursor
				WeatherObject weather = getWeatherFromCursor(cursor);
				// Add weatherObject to the list
				weatherList.add(weather);
			} while (cursor.moveToNext());
		}
		
		// Return weather list
		return weatherList;
	}
	
	// Get database row count
	public int getDatabaseRowsCount() {
		// Get a readable database, execute query and return data into a cursor object
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		cursor.close();
		
		// Return rows count
		return cursor.getCount();
				
	}
	
	// Get weather from cursor into a WeatherOject
	private static WeatherObject getWeatherFromCursor(Cursor cursor) {
		WeatherObject weather = new WeatherObject();
		weather.setId(cursor.getInt(0));
		weather.setLocation(cursor.getString(1));
		weather.setIcon(cursor.getInt(2));
		weather.setTemperature(cursor.getString(3));
		weather.setDescription(cursor.getString(4));
		weather.setPrecipitation(cursor.getString(5));
		weather.setWind(cursor.getString(6));
		weather.setHumidity(cursor.getString(7));
		weather.setPressure(cursor.getString(8));
		weather.setUnit(cursor.getInt(9));
		weather.setType(cursor.getInt(10));
		
		// Return weatherObject
		return weather;
	}

	// Transfer weather object to content values
	private static ContentValues convertWeatherToContentValues(WeatherObject weather) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_LOCATION, weather.getLocation());
		cv.put(KEY_ICON, weather.getIcon());
		cv.put(KEY_TEMPERATURE, weather.getTemperature());
		cv.put(KEY_DESCRIPTION, weather.getDescription());
		cv.put(KEY_PRECIPITATION, weather.getPrecipitation());
		cv.put(KEY_WIND, weather.getWind());
		cv.put(KEY_HUMIDITY, weather.getHumidity());
		cv.put(KEY_PRESSURE, weather.getPressure());
		cv.put(KEY_UNIT, weather.getUnit());
		cv.put(KEY_TYPE, weather.getType());
		// Return cv
		return cv;
	}
	
	// Read all tomorrow weatherObjects from database
	private List<Integer> getTomorrowWeatherIDs() {
		// Get a readable database, execute query and return data into a cursor object
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TYPE + " = " + WeatherObject.TOMORROW, null);
		
		// Loop each cursor object and add ID's to list
		List<Integer> idList = new ArrayList<Integer>();
		if (cursor.moveToFirst()) {
			do {
				// Get weather id from cursor
				idList.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		// Return weather list
		return idList;
	}
}
