package com.allen.design.permanentservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class NeverStopBackupService extends Service {

    Context context;
    private int i;
    private static Handler backTaskHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        backTaskHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                //check running service
                if(!Tool.isServiceRunning(NeverStopService.class, context)) {
                    Intent intent = new Intent(context, NeverStopService.class);
                    startService(intent);
                };
                backTaskHandler.sendEmptyMessageDelayed(1, 1000);
            }
        };
        backTaskHandler.sendEmptyMessageDelayed(1, 1000);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(!Tool.isServiceRunning(NeverStopService.class, context)) {
            Intent intent = new Intent(context, NeverStopService.class);
            startService(intent);
        };
        super.onDestroy();
    }
}
