package com.project.liveat500px.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.project.liveat500px.Dao.DialysisAlarm;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.LoginActivity;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fame on 11/5/2017.
 */

public class NotiGraphService extends Service {
    DataBaseHelper myDb;
    String id;
    Calendar cal;

    public NotiGraphService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start noti graph");
        checkGraphNoti();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//
//        //Intent intent = new Intent("com.android.ServiceStopped");
//        Intent intent = new Intent(getBaseContext(), NotiGraphBoardCast.class);
//        sendBroadcast(intent);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        myDb = new DataBaseHelper(this);

    }

    private void checkGraphNoti(){

        id = myDb.getAllData();

        if (!id.isEmpty()) {
            Call<Patient> call = HttpManager.getInstance().getService().findPatient(id);
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if(response != null){
                        Patient patient = response.body();
                        if (patient.getNotiStatus() ==0 ){

                            cal = Calendar.getInstance();
                            int month = cal.get(Calendar.MONTH)+1;

                            final Call<List<Record>> record = HttpManager.getInstance().getService().findRecordByByte(id, month);
                            record.enqueue(new Callback<List<Record>>() {
                                @Override
                                public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                                    if(response != null){
                                        List<Record> records = response.body();

                                        if(records.size() >=2 && records.get(records.size()-2).getTotalProfit() - records.get(records.size()-1).getTotalProfit() >= 450){

                                            Call<Void> setOne = HttpManager.getInstance().getService().setNotiGraphOne(id);
                                            setOne.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    showNotification(id);

                                                }
                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    System.out.println("failure set one" + t.toString());
                                                }
                                            });
                                        }else{ // น้อยกว่า 450

                                        }

                                    }else {
                                        System.out.println("failure find total record noti :"+response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Record>> call, Throwable t) {
                                    System.out.println("failure find total record noti :"+t.toString());
                                }
                            });
                        }else{ // noti แล้ว

                        }

                        setTimeToNoti();
                        stopService(new Intent(NotiGraphService.this, NotiGraphService.class));
                    }else{
                        System.out.println("find patient to noti graph error "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    System.out.println("find patient to noti graph faliure "+t.toString());
                }
            });
        }
    }

    public void showNotification(String id){

        String content = "ค่าสุทธิของคุณมีค่าผิดปกติ";
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Patient Dialy")
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000 , notification);

        Date d = new Date();

        DialysisAlarm dia = new DialysisAlarm();
        dia.setNotiName(content);
        dia.setPatient_patId_fk(id);
        dia.setNotiType(1);
        dia.setNotiDate(d);
        Call<DialysisAlarm> saveNoti = HttpDateResponse.getInstance().getService().saveNoti(dia);
        saveNoti.enqueue(new Callback<DialysisAlarm>() {
            @Override
            public void onResponse(Call<DialysisAlarm> call, Response<DialysisAlarm> response) {

            }

            @Override
            public void onFailure(Call<DialysisAlarm> call, Throwable t) {
                System.out.println("failure save not "+t.toString());
            }
        });

    }

    private void setTimeToNoti(){

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.add(Calendar.DATE , +1);

        System.out.println("cal noto praph :"+cal2.getTime());
        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(getBaseContext(), NotiGraphReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), pendingIntent);
    }

}
