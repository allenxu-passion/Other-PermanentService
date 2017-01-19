package com.allen.design.permanentservice;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //start jobschedule task
        JobScheduler scheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(),
                JobScheduleTask.class.getName()));
        builder.setPeriodic(5000);
        builder.setPersisted(true);
        if(scheduler.schedule(builder.build())<=0) {
            Log.e("JobScheduler Error", "scheduler.schedule(builder.build())<=0");
        }

        //check service running
        if(!Tool.isServiceRunning(NeverStopService.class, getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), NeverStopService.class);
            startService(intent);
        };

    }
}
