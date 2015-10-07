package hu.u_szeged.android.tender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WiFiStateChangedReceiver extends BroadcastReceiver {
    private static final String TAG = WiFiStateChangedReceiver.class.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Something happened...");
        if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            Log.d(TAG,"WiFi state changed!");
        }
    }
}
