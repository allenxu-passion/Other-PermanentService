package com.allen.design.permanentservice;

import java.util.List;
import android.content.Context;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;

public class Tool {

    private static ActivityManager manager;

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        if(manager==null)
            manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> list = manager.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo service : list) {
            if (service!=null && service.service!=null
                    && serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
