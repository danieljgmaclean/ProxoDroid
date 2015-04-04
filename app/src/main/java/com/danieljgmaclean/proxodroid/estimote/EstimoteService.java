package com.danieljgmaclean.proxodroid.estimote;

/**
 * Created by danieljgmaclean on 22/03/15.
 */
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EstimoteService extends Service {
    private static final String TAG = "EstimoteService";

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Binding the Estimote Manager");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.i(TAG, "Starting the Estimote Manager");
            EstimoteManager.Create((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE), this, intent);
        } catch (Exception e) {
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Stopping the Estimote Manager");
        EstimoteManager.stop();
    }
}