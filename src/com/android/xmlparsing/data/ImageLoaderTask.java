package com.android.xmlparsing.data;

import java.io.IOException;

import com.android.xmlparsing.SimpleAdapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ImageLoaderTask extends AsyncTask<Void, Void, Bitmap> {
	
	private DataItem item;
	private SimpleAdapter listView;
	private int position;

	public ImageLoaderTask(DataItem dataSet, SimpleAdapter simpleAdapter, int position) {
		this.item = dataSet;
		this.listView = simpleAdapter;
		this.position = position;
	}

	@Override
	protected void onPostExecute(Bitmap image) {		
		listView.notifyDataSetChanged();
		super.onPostExecute(image);		
	}
	
	
	@Override
	protected Bitmap doInBackground(Void... params) {
		try {
			Bitmap image;
			image = DataUtils.DownloadImage(item.getUrl());
			item.setImage(image);
			return image;			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
