package com.danieljgmaclean.proxodroid.contracts;

import android.content.Context;

import com.danieljgmaclean.proxodroid.R;

public class CouchAPI {

    private static final String TAG = "CouchAPISingleton";
    private static CouchAPI instance = null;

    protected CouchAPI() {
    }

    public static CouchAPI getInstance() {
        if(instance == null) {
            instance = new CouchAPI();
        }
        return instance;
    }

    public static void connect(Context ctx, String databaseIP, String databaseName){
        GetTask connectTask = new GetTask(ctx.getString(R.string.action_connected));
        connectTask.execute(databaseIP, databaseName);
    }

    public static void postDocument(Context ctx, String databaseIP, String databaseName, String message){
        PostTask postTask = new PostTask(ctx.getString(R.string.action_post));
        postTask.execute(databaseIP, databaseName, message);
    }

}
