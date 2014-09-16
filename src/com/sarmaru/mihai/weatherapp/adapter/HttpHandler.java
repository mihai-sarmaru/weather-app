package com.sarmaru.mihai.weatherapp.adapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpHandler {
	
	// Response
	String responseString = null;
	
	// Constructor
	public HttpHandler() {}
	
	// HTTP request via POST - returns string response
	public String makeHttpCall(String url, String header, String apiKey) {
		try {
			// Get a HTTP client
			DefaultHttpClient client = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse response = null;
			
			// Create and execute a new POST HTTP request
			HttpPost post = new HttpPost(url);
			// Add API Key in header
			post.addHeader(header, apiKey);
			response = client.execute(post);
			
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
