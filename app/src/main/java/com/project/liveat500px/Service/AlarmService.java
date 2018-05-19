package com.project.liveat500px.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Timer;

/**
 * Created by Fame on 10/28/2017.
 */

public class AlarmService extends Service {

    DataBaseHelper myDb;
    private Timer timer;
    String id ;
    SimpleDateFormat dateParse;
    int count = 0 ;

    public AlarmService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(),"noti",Toast.LENGTH_LONG).show();
        System.out.println("start alert service");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("detestor alert service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("clear alert service");
        //Intent intent = new Intent("com.android.ServiceStopped");
        if(count ==0){
            Intent intent = new Intent(getBaseContext(), ReceiveBoardCast.class);
            sendBroadcast(intent);
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();
    }

    public void showNotification(){

        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Patient Dialy")
                .setContentText("Helllo")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000 , notification);
        count++;
    }
}
