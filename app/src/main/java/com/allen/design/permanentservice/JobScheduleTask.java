package com.allen.design.permanentservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class JobScheduleTask extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        //check running service
        if(!Tool.isServiceRunning(NeverStopService.class, this)) {
            Intent intent = new Intent(this, NeverStopService.class);
            startService(intent);
        };
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
