package com.example.android_trackball;

import android.app.Application;
import android.util.DisplayMetrics;

public class DoveboxApp extends Application {

	/** 屏幕宽 */
	public int screenWidth;
	/** 屏幕高 */
	public int screenHeight;

	private static DoveboxApp doveboxApp = new DoveboxApp();
	public static DoveboxApp getInstance(){
		return doveboxApp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}
}
