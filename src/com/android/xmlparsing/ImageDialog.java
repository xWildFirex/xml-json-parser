package com.android.xmlparsing;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageDialog extends Dialog{
	
	private ImageView myDialog;
	private TextView some_text;
	private Bitmap image;	
	private String message;
	
	
	public ImageDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ImageDialog(Context context, Bitmap image) {
		this(context);
		this.image = image;
	}
	
	public ImageDialog(Context context, String message){
		this(context);
		this.message = message;		
	}
	
	public Bitmap getImage(){
		return image;
	}	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_dialog_item);
		
		myDialog = (ImageView)findViewById(R.id.my_image);
		some_text = (TextView)findViewById(R.id.some_text);
		myDialog.setClickable(true);
		if( image!= null) {
		myDialog.setImageBitmap(image);
		} else {
			myDialog.setImageResource(android.R.drawable.ic_delete);		
			some_text.setText(message);			
		}
				
		myDialog.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}
