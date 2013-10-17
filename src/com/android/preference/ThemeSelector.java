package com.android.preference;

import com.android.xmlparsing.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListAdapter;


public class ThemeSelector extends ListPreference {

	public ThemeSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(getSharedPreferences().getString(getKey(), "1"));
		
		/*ListAdapter listAdapter = (ListAdapter) new ThemeArrayAdapter(getContext(),
				R.layout.my_list_preference_item, this.getEntryValues(), index, this);
		builder.setAdapter(listAdapter, this);*/
		super.onPrepareDialogBuilder(builder);
	}

}
