package com.danieljgmaclean.proxodroid.contracts;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.danieljgmaclean.proxodroid.events.GetTaskEvent;
import com.danieljgmaclean.proxodroid.events.MyBus;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by danieljgmaclean on 22/03/15.
 */

public class GetTask extends AsyncTask<String, Void, String> {

    private static String TAG = "GetTask";
    private String action;
    private Context ctx;

    public GetTask(String action){
        this.action = action;
    }

    @Override
    protected String doInBackground(String... params) {
        if(params[0].equals("") || params[1].equals("")){
            return "Failed";
        }
        HttpClient client = new DefaultHttpClient();
        URI website = null;
        try {
            website = new URI(params[0] + "/" + params[1]);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet request = new HttpGet();
        request.setURI(website);
        HttpResponse response = null;
        String data = null;
        try {
            response = client.execute(request);

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
        Log.i(TAG, "Data: " + data);
        MyBus.getInstance().post(new GetTaskEvent(action, data));
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}