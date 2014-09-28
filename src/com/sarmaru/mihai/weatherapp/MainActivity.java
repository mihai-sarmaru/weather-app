package com.sarmaru.mihai.weatherapp;

import java.util.List;

import com.sarmaru.mihai.weatherapp.adapter.DatabaseHandler;
import com.sarmaru.mihai.weatherapp.adapter.HttpHandler;
import com.sarmaru.mihai.weatherapp.adapter.JsonParser;
import com.sarmaru.mihai.weatherapp.adapter.TabsPagerAdapter;
import com.sarmaru.mihai.weatherapp.adapter.Utils;
import com.sarmaru.mihai.weatherapp.adapter.WeatherObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private ActionBar actionBar;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;
	private WeatherPreferences weatherPrefs;
	
	// Database Handler
	DatabaseHandler db = null;

	// Progress dialog used for async task
	private ProgressDialog progressDialog = null;
	
	// Weather objects
	private WeatherObject todayWeather = null;
	private List<WeatherObject> tomorrowWeather = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize weather preferences and database
		weatherPrefs = new WeatherPreferences(this);
		db = new DatabaseHandler(this);
		
		// Tab titles
		final String[] tabNames = getResources().getStringArray(R.array.weather_tab_names);
		
		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		// Set adapter and ActionBar navigation modes
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Add tabs to ActionBar
		for (String tabName : tabNames) {
			actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
		}
		
		// Action for page change
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// Select tab on page change
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
		
		// Execute refresh weather method
		refreshWeather();
	}
	
	public void refreshWeather() {
		// Set weather objects to null before async task
		todayWeather = null;
		tomorrowWeather = null;
		
		// Execute background task to get and parse weather
		new ProcessWeatherJsonAsync().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Switch menu items based on ID
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_refresh:
			refreshWeather();
			break;
		case R.id.menu_location:
			WeatherPreferences.changeLocation(MainActivity.this);
			break;
		case R.id.menu_units:
			WeatherPreferences.changeUnits(MainActivity.this);
			break;
		}
		// Return item
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Set corresponding fragment on tab selection
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {	}
	
	// Subclass for processing JSON string in Async
	private class ProcessWeatherJsonAsync extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			// Show a progress dialog
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage(getString(R.string.progress_dialog_message));
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			// Get location and units shared preferences
			String userLocation = weatherPrefs.getUserLocation();
			int userUnits = weatherPrefs.getUserUnits();

			// Check Internet connection
			if (Utils.isNetworkAvailable(MainActivity.this)) {
				
				// Format URL string
				HttpHandler todayHandler = new HttpHandler();
				String todayFormatUrl = Utils.formatUrlString(getString(R.string.open_weather_maps_url), userLocation, userUnits, WeatherObject.TODAY);
				// Make HTTP call and get a JSON response
				String todayJsonString = todayHandler.makeHttpCall(todayFormatUrl, getString(R.string.open_weather_maps_header), getString(R.string.open_weather_maps_api_key));
				// Parse JSON to a weather object
				todayWeather = JsonParser.parseWeatherJson(todayJsonString, userUnits);
				
				// Format URL string
				HttpHandler tomorrowHandler = new HttpHandler();
				String tomorrowFormatUrl = Utils.formatUrlString(getString(R.string.open_weather_maps_forecast_url), userLocation, userUnits, WeatherObject.TOMORROW);
				// Make HTTP call and get a JSON response
				String tomorrowJsonString = tomorrowHandler.makeHttpCall(tomorrowFormatUrl, getString(R.string.open_weather_maps_header), getString(R.string.open_weather_maps_api_key));
				// Parse JSON to a weather object
				tomorrowWeather = JsonParser.parseForecastJson(tomorrowJsonString, userUnits);
				
				if (todayWeather != null && tomorrowWeather != null) {
					// If database is empty -> create, else -> update
					if (db.getDatabaseRowsCount() == 0) {
						// Insert weather objects
						db.insertTodayWeatherObject(todayWeather);
						db.insertTomorrowWeatherObjects(tomorrowWeather);
					} else {
						// Update weather objects
						db.updateTodayWeatherObject(todayWeather);
						db.updateTomorrowWeatherObject(tomorrowWeather);
					}
				}
				
			} else if (db.getDatabaseRowsCount() != 0) {
				// Get weather and forecast from database
				todayWeather = db.getTodayWeatherObject();
				tomorrowWeather = db.getTomorrowWeatherObjects();
			}
			
			// Return null when finished background task
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (todayWeather != null && tomorrowWeather != null) {
				// Display weather object in views
				TodayFragment.displayTodayWeather(MainActivity.this, todayWeather);
				// Display tomorrow forecast
				TomorrowFragment.displayTomorrowWeather(MainActivity.this, tomorrowWeather);
				
				if (!Utils.isNetworkAvailable(MainActivity.this)) {
					// Notify user for no Internet connection
					Toast.makeText(MainActivity.this, R.string.no_internet_error, Toast.LENGTH_LONG).show();
				}
				
			} else {
				// Notify user that response was negative
				Toast.makeText(MainActivity.this, R.string.api_error, Toast.LENGTH_LONG).show();
			}
			
			// Dismiss progress dialog
			progressDialog.dismiss();
		}
	}
}
