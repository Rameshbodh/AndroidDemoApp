package com.example.schedulerexample.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeChangedFromSettingReceiver extends BroadcastReceiver {

    private static final String TAG = TimeChangedFromSettingReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "Received For Time Change From Settings");
    }
}
