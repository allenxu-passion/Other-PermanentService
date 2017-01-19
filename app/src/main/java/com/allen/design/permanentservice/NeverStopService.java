package com.allen.design.permanentservice;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class NeverStopService extends Service {

    private int FOREGROUND_ID = 100;
    private Context context;
    private static WakeLock wl;
    private static Handler taskHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        if(wl==null) {
            PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RunningAllTheTime");
            wl.acquire();
        }

        Notification.Builder builder = new Notification.Builder(this);
        builder.setOngoing(true)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("Permanent Service")
                .setContentText("This is a demo for permanent service.");
        Notification notify = builder.build();
        notify.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        startForeground(FOREGROUND_ID, notify);

        taskHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //check running service
                if(!Tool.isServiceRunning(NeverStopBackupService.class, context)) {
                    Intent intent = new Intent(context, NeverStopBackupService.class);
                    startService(intent);
                };
                taskHandler.sendEmptyMessageDelayed(0, 1000);
            }
        };

        taskHandler.sendEmptyMessageDelayed(0, 1000);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(!Tool.isServiceRunning(NeverStopBackupService.class, context)) {
            Intent intent = new Intent(context, NeverStopBackupService.class);
            startService(intent);
        };
        super.onDestroy();
    }
}
