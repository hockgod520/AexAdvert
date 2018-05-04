package com.androidex.advert.recives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "aexHome";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO 开机启动
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.v(TAG, String.format("Recive %s.\n", intent.getAction()));
            Intent dlIntent = new Intent(context, com.androidex.advert.MainActivity.class);
            dlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dlIntent);
        }
    }
}

