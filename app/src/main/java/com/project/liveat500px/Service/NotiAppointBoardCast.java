
package com.project.liveat500px.Service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Fame on 11/6/2017.
 */

public class NotiAppointBoardCast extends ReceiveBoardCast {

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("appotintment noti reciever");
        Intent background = new Intent(context, NotiService.class);
        context.startService(background);
    }
}