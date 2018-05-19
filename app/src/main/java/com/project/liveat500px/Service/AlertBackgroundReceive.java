package com.project.liveat500px.Service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Fame on 10/28/2017.
 */

public class AlertBackgroundReceive extends ReceiveBoardCast {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("alarm service boardcast");
        Intent background = new Intent(context, AlarmService.class);
        context.startService(background);

    }
}
