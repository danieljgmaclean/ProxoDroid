package com.danieljgmaclean.proxodroid.contracts;

import android.os.AsyncTask;
import android.util.Log;

import com.danieljgmaclean.proxodroid.utils.Utils;
import com.danieljgmaclean.proxodroid.events.MyBus;
import com.danieljgmaclean.proxodroid.events.PostTaskEvent;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by danieljgmaclean on 22/03/15.
 */

public class PostTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "PostTask";
    private final String action;

    public PostTask(String action){
        this.action = action;
    }
    
    @Override
    protected String doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        URI website = null;
        try {
            website = new URI(params[0] + "/" + params[1]);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpPost httpost = new HttpPost();
        httpost.setURI(website);

        //convert parameters into JSON object
        JSONObject holder = null;
        try {
            holder = new JSONObject();
            holder.put("message", params[2]);
            holder.put("time", Utils.getDateTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //passes the results to a string builder/entity
        StringEntity se = null;
        try {
            se = new StringEntity(holder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        HttpResponse response = null;
        String data = null;
        try {
            response = client.execute(httpost);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while ((l = in.readLine()) !=null){
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();

            Log.i(TAG, "Couch responded with: " + response.getStatusLine().getStatusCode());
            Log.i(TAG, "Couch responded with: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        MyBus.getInstance().post(new PostTaskEvent(action, data));
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}