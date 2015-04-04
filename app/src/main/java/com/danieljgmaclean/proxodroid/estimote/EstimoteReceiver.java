package com.danieljgmaclean.proxodroid.estimote;

/**
 * Created by danieljgmaclean on 22/03/15.
 */

import android.content.BroadcastReceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EstimoteReceiver extends BroadcastReceiver {
    private static final String TAG = "EstimoteReceiver";
    private Intent estimoteServiceIntent;

    // Method called when bluetooth is turned on or off.
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i(TAG, "Bluetooth was turned OFF");
                    // When bluetooth is turning off, lets stop estimotes ranging
                    if (estimoteServiceIntent != null) {
                        context.stopService(estimoteServiceIntent);
                        estimoteServiceIntent = null;
                    }
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.i(TAG, "Bluetooth was turned ON");
                    // When bluethooth is turned on, let's start estimotes monitoring
                    if (estimoteServiceIntent == null) {
                        estimoteServiceIntent = new Intent(context,EstimoteService.class);
                        context.startService(estimoteServiceIntent);
                    }
                    break;
            }
        }
    }
}
