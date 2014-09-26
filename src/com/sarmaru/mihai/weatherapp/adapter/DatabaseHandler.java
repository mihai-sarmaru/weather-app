package com.sarmaru.mihai.weatherapp.adapter;

import android.content.Context;
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

}
