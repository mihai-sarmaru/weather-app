package com.sarmaru.mihai.weatherapp;

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

	// Progress dialog used for async task
	private ProgressDialog progressDialog = null;
	
	// Weather objects
	private WeatherObject todayWeather = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		
		// Check Internet connection
		if (Utils.isNetworkAvailable(this)) {
			// Execute background task to get and parse weather
			new ProcessWeatherJsonAsync().execute();
		} else {
			// TODO get info from database
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
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
			
			// TODO SHARED PREFERENCES for LOCATION and UNITS
			
			// Format URL string
			HttpHandler handler = new HttpHandler();
			String formatUrl = Utils.formatUrlString(getString(R.string.open_weather_maps_url), "Galati", WeatherObject.DEFAULT);
			// Make HTTP call and get a JSON response
			String jsonString = handler.makeHttpCall(formatUrl, getString(R.string.open_weather_maps_header), getString(R.string.open_weather_maps_api_key));
			// Parse JSON to a weather object
			todayWeather = JsonParser.parseWeatherJson(jsonString, WeatherObject.DEFAULT, WeatherObject.TODAY);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (todayWeather != null) {
				// Display weather object in views
				TodayFragment.displayTodayWeather(MainActivity.this, todayWeather);
				
				// Dismiss progress dialog
				progressDialog.dismiss();
			} else {
				// Notify user that response was negative
				Toast.makeText(MainActivity.this, R.string.api_error, Toast.LENGTH_LONG).show();
			}

		}
		
	}
}
