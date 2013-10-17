package com.android.fragment;

import com.android.xmlparsing.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyDialogWindowFragment extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// Create a view by inflating desired layout
		View v = inflater.inflate(R.layout.my_dialog_item, container, false);
		// you can locate a view and set values
		TextView tv = (TextView)v.findViewById(R.id.some_text);
		tv.setText("message");	
		
		return v;
	}
	
	
	/*This 
	works well for simple dialogs. The first option of overriding onCreateView()is equally 
	easy and provides much more flexibility. 
	@Override*/
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
			.setTitle("This is my Dialog")
			.setPositiveButton("Ok", (OnClickListener) this)
			.setNegativeButton("Cancel", (OnClickListener) this)
			.setMessage(this.getMessage());
		return b.create();
	}

	private CharSequence getMessage() {
		// TODO Auto-generated method stub
		String message = "My Message";
		return message;
	}

}
