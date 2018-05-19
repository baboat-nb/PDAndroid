package com.project.liveat500px.Service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Fame on 11/2/2017.
 */

public class StopAlarmReceive extends AlarmReciever {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("stop alarm receiver");
    }
}
