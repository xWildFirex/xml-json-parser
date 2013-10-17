package com.android.xmlparsing;


import com.android.preference.MyPreferenceActivity;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends DefaulActivity implements OnClickListener {
	
	Resources resources;
	Context context;	
	
	Button xmlButton;
	Button jsonButton;
	//String xmlUrl = "http://10.201.1.227/structure.xml";
	String xmlUrl = "https://github.com/xWildFirex/xml-json-parser/blob/master/structures/structure.xml";
	String jsonUrl = "https://github.com/xWildFirex/xml-json-parser/blob/master/structures/structure.json";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		
		resources = this.getResources();
				
		PreferenceManager.setDefaultValues(this, R.xml.my_preference, false);
		xmlButton = (Button)findViewById(R.id.xml_button);
		jsonButton = (Button)findViewById(R.id.json_button);
		
		xmlButton.setOnClickListener(this);
		jsonButton.setOnClickListener(this);		
		
		setBackground();
	
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, AndroidXMLParsingActivity.class);		
		switch (v.getId()) {
		case R.id.xml_button:
			intent.putExtra("URL", xmlUrl);
			intent.putExtra("BUTTON", 1);
			startActivity(intent);	
			break;
			
		case R.id.json_button:
			intent.putExtra("URL", jsonUrl);			
			startActivity(intent);
			break;

		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.main_preferences)
		{
			Intent intent = new Intent()
				.setClass(this, MyPreferenceActivity.class);
			this.startActivityForResult(intent, 0);
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		super.onActivityResult(requestCode, resultCode, data);
		setBackground();
	}
	
	public Context getContext() {
		return context;
		
	}

	void setBackground() {
		SharedPreferences pref =
				PreferenceManager.getDefaultSharedPreferences(this);
		String[] optionText = resources.getStringArray(R.array.options_values);
		String option = pref.getString(
				resources.getString(R.string.main_list_preference),
				null);
		View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
		
		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			switch (Integer.parseInt(option)) {
			case 1:		
				view.setBackgroundResource(R.drawable.strand_lnd);
				
				break;
			case 2:				
				view.setBackgroundResource(R.drawable.rain_drops_lnd);
				break;
			case 3:		
				view.setBackgroundResource(R.drawable.games);
				break;
			default:
				view.setBackgroundResource(R.drawable.silver_spatter_lnd);
				break;
			}
			
		} else {
			switch (Integer.parseInt(option)) {
			case 1:		
				view.setBackgroundResource(R.drawable.strand);
				break;
			case 2:				
				view.setBackgroundResource(R.drawable.rain_drops);
				break;
			case 3:		
				view.setBackgroundResource(R.drawable.games);
				break;
			default:
				view.setBackgroundResource(R.drawable.silver_spatter);
				break;
			}
		}
	}

}
