package com.danieljgmaclean.proxodroid.events;

import android.util.Log;

import com.estimote.sdk.Beacon;

import java.util.List;

/**
 * Created by danieljgmaclean on 22/03/15.
 */
public class EstimotesFoundEvent extends Event{

    private static final String TAG = "EstimotesFoundEvent";
    private List<Beacon> beacons;

    public EstimotesFoundEvent(String action, List<Beacon> beacons) {
        this.action = action;
        this.beacons = beacons;
        Log.i(TAG, "New EstimotesFoundEvent created");
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }
}