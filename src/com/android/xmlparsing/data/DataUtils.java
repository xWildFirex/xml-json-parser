package com.android.xmlparsing.data;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DataUtils {
	
	
	
    public static String loadFromUrl(String url) throws IOException {
        try {
        	HttpClient httpClient = CustomHttpClient.getHttpClient();
			
			HttpGet httpGet = new HttpGet(url);

			//HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String content = EntityUtils.toString(httpEntity);
			return content;
		} catch (IllegalArgumentException e) {
			System.out.print(" ");
			e.printStackTrace();
		}
        return null;
    }

    public static String getValue(Element parent, String element) {
        NodeList n = parent.getElementsByTagName(element);
        return getElementValue(n.item(0));
    }

    public static String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    
    public static List<DataItem> parseJson(String json) throws  JSONException{

        List<DataItem> dataItems = new ArrayList<DataItem>();
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject("response");

        JSONArray itemArray = response.getJSONArray("items");

        for (int i=0; i<itemArray.length(); i++)
        {
            String url = itemArray.getJSONObject(i).getString("url").toString();
            String text = itemArray.getJSONObject(i).getString("text").toString();
            // 	TODO 
            // Add here new child of tag "items"
            // Before this, change DataItems - add new field for new child.
            // 	Add to SimpleAdapter.bindView setText...for new TV(by Id)

            DataItem dataItem = new DataItem(url, text);
            dataItems.add(dataItem);
        }
        return dataItems;
    }

    public static List<DataItem> parseXml(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<DataItem> dataItems = new ArrayList<DataItem>();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            StringReader characterStream = new StringReader(xml);
            is.setCharacterStream(characterStream);
            doc = db.parse(is);
            NodeList items = doc.getElementsByTagName("items");

            for (int i = 0; i < items.getLength(); i++) {
                Element itemElement = (Element) items.item(i);
                String url = getValue(itemElement, "url");
                String text = getValue(itemElement, "text");
                // TODO 
                // Add here new chilp of tag "items"
                // Before this, change DataItems - add new field for new child.
                // Add to SimpleAdapter.bindView setText...for new TV(by Id)

                DataItem dataItem = new DataItem(url, text);              
                dataItems.add(dataItem);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         return dataItems;
    }
    
    public static Bitmap DownloadImage(String url) throws IOException{
    	URL urlO = new URL(url);
    	new BitmapFactory();
		Bitmap image = BitmapFactory.decodeStream(urlO.openConnection().getInputStream());
		return image;    	
    }

}
