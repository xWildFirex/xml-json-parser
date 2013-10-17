package com.android.xmlparsing;

import android.app.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

public class DefaulActivity extends Activity {
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
		view.setBackgroundResource(R.drawable.rain_drops);
		
		
		
	};	
	
}
