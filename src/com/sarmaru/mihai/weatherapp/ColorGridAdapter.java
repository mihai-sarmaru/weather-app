package com.sarmaru.mihai.weatherapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ColorGridAdapter extends BaseAdapter {
	
	// Private context variable
	private Context mContext;
	private static int[] colorArray = null;
	
	public ColorGridAdapter(Context context) {
		this.mContext = context;
		colorArray = context.getResources().getIntArray(R.array.actionbar_color_array);
	}

	@Override
	public int getCount() {
		return colorArray.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Initialize imageView
		View imageView;
		
		// Recycle imageView from convertView for performance
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(4, 4, 4, 4);
		} else {
			imageView = (ImageView) convertView;
		}
		
		// Set imageView background color and return the view
		imageView.setBackgroundColor(colorArray[position]);
		return imageView;
	}

}
