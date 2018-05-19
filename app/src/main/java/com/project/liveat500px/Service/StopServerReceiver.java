package com.project.liveat500px.Service;

import android.content.Context;
import android.content.Intent;

import com.project.liveat500px.activity.DataBaseHelper;

/**
 * Created by Fame on 10/30/2017.
 */

public class StopServerReceiver extends ReceiveBoardCast {

    @Override
    public void onReceive(Context context, Intent intent) {

        DataBaseHelper myDb = new DataBaseHelper(context);
        myDb.setCountZero();
        Intent background = new Intent(context, NotiService.class);
        context.startService(background);
    }
}
