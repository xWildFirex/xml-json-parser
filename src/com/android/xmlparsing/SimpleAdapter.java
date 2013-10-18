package com.android.xmlparsing;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.android.xmlparsing.data.DataItem;
import com.android.xmlparsing.data.ImageLoaderTask;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends BaseAdapter implements Filterable {
	private String LOG_TAG = "SimpleAdapter";
	
    private int textId, imageId;
    private ViewBinder mViewBinder;
    
  
    
    
    
    private List<DataItem> mData;
    private ImageLoaderTask[] backgroundWorkers;

    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;

    private SimpleFilter mFilter;
    private ArrayList<DataItem> mUnfilteredData;
    private boolean[] isAnimated;
    private ListView listView;   
   

    public SimpleAdapter(Context context, List<DataItem> data, ListView listView,
            int resource, int textId, int imageId) {
        mData = data;
        backgroundWorkers = new ImageLoaderTask[data.size()];
        isAnimated = new boolean[data.size()];
        mResource = mDropDownResource = resource;
       
        this.textId = textId;
        this.imageId = imageId;
        this.listView = listView;
        
      
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mData.size();
    }

    public DataItem getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {  	
    	     	
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        bindView(position, v);
                
        return v;
    }

    public void setDropDownViewResource(int resource) {
        this.mDropDownResource = resource;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mDropDownResource);
    }

    private void bindView(int position, View view) {
        final DataItem dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }

        final ViewBinder binder = mViewBinder;                   
        final View textView = view.findViewById(textId);
        if (textView != null) {
        	String text = dataSet.getText();
        	if (text==null) text = "";
        	setViewText((TextView) textView, text);
        }
        
        final ImageView imageView = (ImageView) view.findViewById(imageId);
        if (imageView != null) {        	
        	Bitmap image = dataSet.getImage();
        	if (image == null){
        		imageView.setImageBitmap(null);
        		if (backgroundWorkers[position]==null){
        			backgroundWorkers[position] = new ImageLoaderTask(dataSet, this, position);
        			backgroundWorkers[position].execute(); 
        		}       		
        	}  
        	else {
        		
        		if (!isAnimated[position]){
        		TranslateAnimation tanim = new TranslateAnimation(600, 0, 10, 0);        		
        		tanim.setDuration(700);
        		tanim.setFillAfter(true);
        		imageView.startAnimation(tanim);
        		isAnimated[position]=true;
        		}
        		imageView.setImageBitmap(image);    
			}        	    	
        }     
        
    }   
  

    public ViewBinder getViewBinder() {
        return mViewBinder;
    }    

    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SimpleFilter();
        }
        return mFilter;
    }

    public static interface ViewBinder {
        boolean setViewValue(View view, Object data, String textRepresentation);
    }
    
  

    private class SimpleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<DataItem>(mData);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<DataItem> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<DataItem> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<DataItem> newValues = new ArrayList<DataItem>(count);

                for (int i = 0; i < count; i++) {
                    DataItem h = unfilteredValues.get(i);
                    if (h != null) {                        
                    	String word = h.getText();
                    	if (word.toLowerCase().contains(prefixString)) {
                            newValues.add(h);                            
                        }
                    	                  	
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData = (List<DataItem>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
