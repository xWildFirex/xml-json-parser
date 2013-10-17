package com.android.xmlparsing.data;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DataItem {
    private String url;
    private String text;
    private Bitmap image;  
    

    public DataItem(String url, String text) {
        this.url = url;
        this.text = text;        
    }
    
    public Bitmap getImage() {
    	return image;
    }
    
    public void setImage(Bitmap image){
    	this.image = image;
    } 

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}