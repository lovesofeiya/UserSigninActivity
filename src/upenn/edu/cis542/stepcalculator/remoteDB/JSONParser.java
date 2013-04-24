package upenn.edu.cis542.stepcalculator.remoteDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
 
public class JSONParser {
	
	private static final String tag = "remoteDB";
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    
    // constructor
    public JSONParser() {
    	Log.d(tag, "create JSONParser");
    }
 
    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
 
        // Making HTTP request
        try { 
            // check for request method
            if(method == "POST"){
    	    	Log.d(tag, "pass5");
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
 
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
    	    	Log.d(tag, "pass6");
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            //if(is == null) Log.d(tag, "is is null");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e(tag, "Error converting result " + e.toString());
	    	Log.d(tag, "pass7");
            
            return null;
        }
 
        // try parse the string to a JSON object
        try {
        	//Log.e(tag,  json);
            jObj = new JSONObject(json);
        } catch (JSONException e) {
        	//mHandler.obtainMessage(MainActivity.CONNECT_FAIL).sendToTarget();
            Log.e(tag, "Error parsing data " + e.toString());
            
            return null;
        }
 
        // return JSON String
        return jObj;
 
    }
}