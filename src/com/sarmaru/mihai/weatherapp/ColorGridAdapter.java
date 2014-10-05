package com.sarmaru.mihai.weatherapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ColorGridAdapter extends BaseAdapter {
	
	// Public colorGrid color arrays
	public static final int[] actionBarColorArray = {R.color.actionbar_turquoise, R.color.actionbar_green,
		R.color.actionbar_blue, R.color.actionbar_purpule, R.color.actionbar_indigo,
		R.color.actionbar_yellow, R.color.actionbar_orange, R.color.actionbar_red, R.color.actionbar_gray};
	
	public static final int[] actionBarTabColorArray = {R.color.actionbar_turquoise_dark, R.color.actionbar_green_dark,
		R.color.actionbar_blue_dark, R.color.actionbar_purpule_dark, R.color.actionbar_indigo_dark,
		R.color.actionbar_yellow_dark, R.color.actionbar_orange_dark, R.color.actionbar_red_dark, R.color.actionbar_gray_dark};
	
	// Private context variable
	private Context mContext;
	private static int[] colorArray = null;
	
	public ColorGridAdapter(Context context) {
		this.mContext = context;
		colorArray = context.getResources().getIntArray(R.array.actionbar_color_array);
	}

	@Override
	public int getCount() {
		return actionBarColorArray.length;
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
