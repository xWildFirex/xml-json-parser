package com.android.xmlparsing;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.fragment.DialogFragment;
import com.android.preference.MyPreferenceActivity;
import com.android.xmlparsing.data.DataItem;
import com.android.xmlparsing.data.DataUtils;

public class AndroidXMLParsingActivity extends ListActivity {
		
	String URL;
	Resources resources;	
	
	DisplayMetrics metrics;

	EditText inputSearch;
	List<DataItem> data = null;
	final String LOG_TAG = "myLog";
		
	class XMLLoadTask extends AsyncTask<String, Void, List<DataItem>>{
		
		Boolean loadFromXml;
		
		public XMLLoadTask(Boolean loadFromXml) {
			this.loadFromXml = loadFromXml;			
		}

		@Override
		protected List<DataItem> doInBackground(String... params) {				
			try {
				if (loadFromXml){
					String content = DataUtils.loadFromUrl(params[0]);
					return DataUtils.parseXml(content);					
				} else {
					String content = DataUtils.loadFromUrl(URL);				
					return DataUtils.parseJson(content);
				}
			} catch (IOException e) {				
				e.printStackTrace();
				Log.e(LOG_TAG, e.toString());
				return null;
			} catch (JSONException e) {				
				e.printStackTrace();
				Log.e(LOG_TAG, e.toString());
				return null;
			}
		}
		
		protected void onPostExecute(List<DataItem> result) {
			AndroidXMLParsingActivity.this.data = result;
			AndroidXMLParsingActivity.this.populateData();
		};
		
	}
	
	public void loadDataAsync(){
		Intent intent = getIntent();
		Integer i = intent.getIntExtra("BUTTON", 2);
		URL = intent.getStringExtra("URL");
		new XMLLoadTask(i==1).execute(URL);
		
	}
	
	public void populateData() {
		
		View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
		view.setBackgroundResource(R.drawable.strand_lnd);	
		if (data!=null){
		SimpleAdapter adapter = null;
		adapter = new SimpleAdapter(this, data, getListView(), R.layout.list_row, R.id.text, R.id.lr_imageV);
		setListAdapter(adapter);
		inputSearch = (EditText)findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new MyTextWatcher(adapter));
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if (parent.getAdapter() instanceof SimpleAdapter){
					int orientation = getResources().getConfiguration().orientation;
					SimpleAdapter adapter = (SimpleAdapter) parent.getAdapter();
					ImageDialog iDialog = new ImageDialog(AndroidXMLParsingActivity.this, adapter.getItem(position).getImage());
					if (orientation == Configuration.ORIENTATION_PORTRAIT) {
						
						iDialog.setTitle(adapter.getItem(position).getText());
						iDialog.show();
					} else {
						TranslateAnimation tanim = new TranslateAnimation(600, 0, 10, 0);        		
		        		tanim.setDuration(700);
		        		tanim.setFillAfter(true);
						Fragment myFragment = getFragmentManager().findFragmentById(R.id.picture_fragment);						
						View iv = (ImageView) myFragment.getView().findViewById(R.id.frg_iv);
						iv.startAnimation(tanim);
						((ImageView) iv).setImageBitmap(iDialog.getImage());
						((TextView) myFragment.getView().findViewById(R.id.frg_tv)).setText(adapter.getItem(position).getText());
					}
				}
			}
		});
		} else {
			ImageDialog iDialog = new ImageDialog(this, "Data set is empty");
			iDialog.setTitle("Error");
			iDialog.show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_parsing_a);
			
		resources = this.getResources();		
		setBackground();
        loadDataAsync();
			
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
	
	class MyTextWatcher implements TextWatcher {	
		SimpleAdapter adapter;
		
		public MyTextWatcher(SimpleAdapter adapter) {
			this.adapter = adapter;
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (adapter != null)
			adapter.getFilter().filter(s);
		}			
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {			
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	}
	
	void setBackground() {
		SharedPreferences pref =
				PreferenceManager.getDefaultSharedPreferences(this);		
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