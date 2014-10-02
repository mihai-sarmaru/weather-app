package com.sarmaru.mihai.weatherapp;

import com.sarmaru.mihai.weatherapp.adapter.WeatherObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

public class WeatherPreferences {
	
	// Private preferences member
	private static SharedPreferences prefs;
	
	// Private preferences values
	private static final String PREF_LOCATION = "location";
	private static final String PREF_DEFAULT_LOCATION = "London";
	private static final String PREF_UNITS = "units";
	private static final int PREF_DEFAULT_UNITS = WeatherObject.METRIC;
	
	// Color preferences
	private static final String PREF_COLOR = "color";
	private static final String PREF_COLOR_TABS = "darkcolor";
	private static final int PREF_DEFAULT_COLOR = R.color.actionbar_blue;
	private static final int PREF_DEFAULT_COLOR_TABS = R.color.actionbar_blue_dark;
	
	// Constructor
	public WeatherPreferences(Activity activity) {
		// Get preferences in private mode
		prefs = activity.getPreferences(Activity.MODE_PRIVATE);
	}
	
	// Get location from preferences
	String getUserLocation() {
		return prefs.getString(PREF_LOCATION, PREF_DEFAULT_LOCATION);
	}
	
	// Set location to preferences
	void setUserLocation(String location) {
		prefs.edit().putString(PREF_LOCATION, location).commit();
	}
	
	// Get units from preferences
	int getUserUnits() {
		return prefs.getInt(PREF_UNITS, PREF_DEFAULT_UNITS);
	}
	
	// Set location to preferences
	void setUserUnits(int units) {
		prefs.edit().putInt(PREF_UNITS, units).commit();
	}
	
	// Get ActionBar color
	int getActionBarColor() {
		return prefs.getInt(PREF_COLOR, PREF_DEFAULT_COLOR);
	}
	
	// Set ActionBar color
	void setActionBarColor(int color) {
		prefs.edit().putInt(PREF_COLOR, color).commit();
	}
	
	// Get ActionBar tabs color
	int getActionBarTabsColor() {
		return prefs.getInt(PREF_COLOR_TABS, PREF_DEFAULT_COLOR_TABS);
	}
	
	// Set ActionBar tabs color
	void setActionBarTabsColor(int color) {
		prefs.edit().putInt(PREF_COLOR_TABS, color).commit();
	}
	
	// Location preference dialog
	public static void changeLocation(final Activity activity) {
		final WeatherPreferences wp = new WeatherPreferences(activity);
		
		// Create an Alert Dialog Builder and set title
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.dialog_location_title);
		builder.setCancelable(false);
		
		// Crate an EditText view inside the dialog
		final EditText inputField = new EditText(activity);
		inputField.setInputType(InputType.TYPE_CLASS_TEXT);
		inputField.setHint(R.string.dialog_location_hint);
		builder.setView(inputField);
		
		// Set positive button
		builder.setPositiveButton(R.string.dialog_positive_button, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Change location shared preferences
				wp.setUserLocation(inputField.getText().toString());
				// Recreate Activity after changes
				activity.recreate();
			}
		});
		
		// Set negative button
		builder.setNegativeButton(R.string.dialog_negative_button, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Set previous user location
				wp.setUserLocation(wp.getUserLocation());
			}
		});
		
		// Show dialog
		builder.show();
	}
	
	// Units preference dialog
	public static void changeUnits(final Activity activity) {
		final WeatherPreferences wp = new WeatherPreferences(activity);
		
		// Create an Alert Dialog Builder and set title
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.dialog_units_title);
		builder.setCancelable(true);
		
		// Crate a list from XML array
		builder.setItems(R.array.weather_unit_system, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// which = selected item ID
				if (which == 0) {
					wp.setUserUnits(WeatherObject.METRIC);
				} else if (which == 1) {
					wp.setUserUnits(WeatherObject.IMPERIAL);
				} else {
					wp.setUserUnits(WeatherObject.DEFAULT);
				}
				// Recreate parent activity after changes
				activity.recreate();
			}
		});
		
		// Show dialog
		builder.show();
	}
	
	// Color preferences dialog
	public static void changeColor(final Activity activity) {
		final WeatherPreferences wp = new WeatherPreferences(activity);
		
		final int[] actionBarColorArray = activity.getResources().getIntArray(R.array.actionbar_color_array);
		final int[] actionBarTabColorArray = activity.getResources().getIntArray(R.array.actionbar_dark_color_array);
		
		// Create an Alert Dialog Builder and set title
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.dialog_color_title);
		builder.setCancelable(true);
		
		// Create a GridView to store the colors from XML
		GridView colorGrid = new GridView(activity);
		// Set grid adapter and number of columns
		colorGrid.setNumColumns(3);
		colorGrid.setAdapter(new ColorGridAdapter(activity));
		
		// Set color grid as the dialog's view
		builder.setView(colorGrid);
		
		// Set on item click listener
		colorGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// TODO Set actionBar color preferences

			}
		});
		
		// Show dialog
		builder.show();
	}
}
