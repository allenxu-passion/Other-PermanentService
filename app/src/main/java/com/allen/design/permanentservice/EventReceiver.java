package com.allen.design.permanentservice;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class EventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null)
            return;

        boolean running = Tool.isServiceRunning(NeverStopService.class, context);
        if(!running) {
            Intent serviceIntent = new Intent(context, NeverStopService.class);
            context.startService(serviceIntent);
        }
    }

}
