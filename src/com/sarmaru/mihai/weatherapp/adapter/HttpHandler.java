package com.sarmaru.mihai.weatherapp.adapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpHandler {
	
	// Response
	String responseString = null;
	
	// Constructor
	public HttpHandler() {}
	
	// HTTP request via GET - returns string response
	public String makeHttpCall(String url, String header, String apiKey) {
		try {
			// Get a HTTP client
			DefaultHttpClient client = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse response = null;
			
			// Create and execute a new GET HTTP request
			HttpGet httpGet = new HttpGet(url);
			// Add API Key in header
			httpGet.addHeader(header, apiKey);
			response = client.execute(httpGet);
			
			// Get response from server
			entity = response.getEntity();
			responseString = EntityUtils.toString(entity);
			
		} catch (Exception e) {
			// Log exception and print stack trace
			Log.d("HTTP", "HTTP Handler failed to make call / receive response");
			e.printStackTrace();
		}
		
		// Return response string
		return responseString;
	}

}
