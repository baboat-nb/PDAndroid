package com.project.liveat500px.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.project.liveat500px.activity.ShowEvent;

/**
 * Created by Fame on 10/29/2017.
 */

public class AlarmReciever extends  BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, ShowEvent.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}