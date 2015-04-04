package com.danieljgmaclean.proxodroid.events;

import android.util.Log;

/**
 * Created by danieljgmaclean on 22/03/15.
 */
public class GetTaskEvent extends Event{

    private static final String TAG = "GetTaskEvent";

    public GetTaskEvent(String action, String result) {
        this.result = result;
        this.action = action;
        Log.i(TAG, "New GetTaskEvent created");
    }
}