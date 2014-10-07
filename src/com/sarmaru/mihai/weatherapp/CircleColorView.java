package com.sarmaru.mihai.weatherapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CircleColorView extends View {
	
	// Circle color variables
	private int innerCircleColor, outerCircleColor;
	// Paint global variable
	private Paint paint;
	
	// Radius difference constant
	private static final int RADIUS_DIFFERENCE = 10;

	public CircleColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Initialize paint
		paint = new Paint();
		
		// Get attributes from XML
		TypedArray attrArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleColorView, 0, 0);
		try {
			innerCircleColor = attrArray.getInteger(R.styleable.CircleColorView_innerCircleColor, 0);
			outerCircleColor = attrArray.getInteger(R.styleable.CircleColorView_outerCircleColor, 0);
		} finally {
			attrArray.recycle();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// Get have the with and height of the view
		int viewHalfWidth = this.getMeasuredWidth() / 2;
		int viewHalfHeight = this.getMeasuredHeight() / 2;
		
		// Paint properties - fill style and anti-alias
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		
		// Set outer circle color and draw
		paint.setColor(outerCircleColor);
		canvas.drawCircle(viewHalfWidth, viewHalfHeight, getOuterCircleRadius(viewHalfWidth, viewHalfHeight), paint);
		
		// Set inner circle color and draw
		paint.setColor(innerCircleColor);
		canvas.drawCircle(viewHalfWidth, viewHalfHeight, getInnerCircleRadius(viewHalfWidth, viewHalfHeight), paint);
	}
	
	// Radius = smaller half value of view
	private int getOuterCircleRadius(int halfWidth, int halfHeight) {
		return halfWidth > halfHeight ? halfHeight : halfWidth;
	}
	
	// Radius = smaller half value of view
	private int getInnerCircleRadius(int halfWidth, int halfHeight) {
		return halfWidth > halfHeight ? halfHeight - RADIUS_DIFFERENCE : halfWidth - RADIUS_DIFFERENCE;
	}
	
	// Circle inner color getter and setter
	public int getInnerCircleColor() {
		return innerCircleColor;
	}
	public void setInnerCircleColor(int newColor) {
		innerCircleColor = newColor;
		// Redraw the current view
		invalidate();
		requestLayout();
	}

	// Circle outer color getter and setter
	public int getOuterCircleColor() {
		return outerCircleColor;
	}
	public void setOuterCircleColor(int newColor) {
		outerCircleColor = newColor;
		// Redraw the current view
		invalidate();
		requestLayout();
	}
}
