package com.android.preference;

import com.android.xmlparsing.R;
import com.android.xmlparsing.R.xml;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;

public class MyPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.my_preference);	
		
	}	
	
	
}


