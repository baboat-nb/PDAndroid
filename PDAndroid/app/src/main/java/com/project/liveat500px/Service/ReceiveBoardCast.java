package com.project.liveat500px.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.project.liveat500px.Dao.DialysisAlarm;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.LoginActivity;
import com.project.liveat500px.manager.http.HttpDateResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Fame on 10/28/2017.
 */

public class ReceiveBoardCast extends BroadcastReceiver {
    DataBaseHelper myDb;
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("noti reciece");
        myDb = new DataBaseHelper(context);
        Intent notificationIntent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        Date dateThree = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String dt = df.format(dateThree);

        String monthThree = dt.substring(3,5);
        String dayThree = dt.substring(0,2);
        String yearThree = dt.substring(6,10);
        int intYear = Integer.parseInt(yearThree)+543;

        switch (monthThree) {
            case "01":
                monthThree = "ม.ค.";
                break;
            case "02":
                monthThree = "ก.พ.";
                break;
            case "03":
                monthThree = "มี.ค.";
                break;
            case "04":
                monthThree = "เม.ย.";
                break;
            case "05":
                monthThree = "พ.ค.";
                break;
            case "06":
                monthThree = "มิ.ย.";
                break;
            case "07":
                monthThree = "ก.ค.";
                break;
            case "08":
                monthThree = "ส.ค.";
                break;
            case "09":
                monthThree = "ก.ย.";
                break;
            case "10":
                monthThree = "ต.ค.";
                break;
            case "11":
                monthThree = "พ.ย.";
                break;
            case "12":
                monthThree = "ธ.ค.";
                break;
        }

        String allDate = dayThree+" "+monthThree+" "+intYear;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Patient Dialy")
                .setContentText("ท่านมีนัดพบแพทย์วันที่ "+allDate)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000 , notification);

        String content = "ท่านมีนัดพบแพทย์วันที่ "+allDate;



        Intent background = new Intent(context, NotiService.class);
        context.startService(background);

        String id = myDb.getAllData();

        Date d = new Date();
        DialysisAlarm dia = new DialysisAlarm();
        dia.setNotiName(content);
        dia.setPatient_patId_fk(id);
        dia.setNotiType(0);
        dia.setNotiDate(d);
        Call<DialysisAlarm> saveNoti = HttpDateResponse.getInstance().getService().saveNoti(dia);
        saveNoti.enqueue(new Callback<DialysisAlarm>() {
            @Override
            public void onResponse(Call<DialysisAlarm> call, Response<DialysisAlarm> response) {
                if(response != null){
                    System.out.println("success : noti save appoeintment");
                }
            }

            @Override
            public void onFailure(Call<DialysisAlarm> call, Throwable t) {
                System.out.println("failure save not "+t.toString());
            }
        });

    }

}

