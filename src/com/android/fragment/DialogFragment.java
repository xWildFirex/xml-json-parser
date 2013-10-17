package com.android.fragment;

import com.android.mymapapp.MapActivity;
import com.android.xmlparsing.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class DialogFragment extends Fragment {
	
	Button frg_button;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment, null); 
		
		frg_button = (Button) v.findViewById(R.id.frg_button);
		
		frg_button.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	  Intent intent = new Intent(getActivity(), MapActivity.class);
		    	  startActivity(intent);
		      }
		    });
		
		return v;
	}	
	
}
