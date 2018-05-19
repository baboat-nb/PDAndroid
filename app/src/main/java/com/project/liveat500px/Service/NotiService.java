package com.project.liveat500px.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.onesignal.OneSignal;
import com.project.liveat500px.Dao.Appointment;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.LoginActivity;
import com.project.liveat500px.activity.MainActivity;
import com.project.liveat500px.manager.http.HttpManager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotiService extends Service {

    DataBaseHelper myDb;
    private Timer timer;
    String id;
    SimpleDateFormat dateParse;
    int count;

    public NotiService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start noti appoint : ");
        setTimeNoti();
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

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Intent intent = new Intent(getBaseContext(), NotiAppointBoardCast.class);
        sendBroadcast(intent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        myDb = new DataBaseHelper(this);

    }

    public void setTimeNoti() {
        id = myDb.getAllData();

        if (!id.isEmpty()) {
            Call<List<Appointment>> call = HttpManager.getInstance().getService().findAppointmentSort(id);
            call.enqueue(new Callback<List<Appointment>>() {
                @Override
                public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                    if (response != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            count = i;
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.DATE, +3);

                            int day = response.body().get(i).getAppDate().getDate();
                            String dayString = String.valueOf(day);
                            if (dayString.length() == 1) {
                                dayString = "0" + dayString;
                            }
                            int month = response.body().get(i).getAppDate().getMonth() + 1;
                            int year = response.body().get(i).getAppDate().getYear() + 1900;
                            String dateTime = year + "-" + month + "-" + dayString;

                            dateParse = new SimpleDateFormat("yyyy-MM-dd");
                            System.out.println("date appointment :"+dateTime);
                            System.out.println("check date noti app : "+dateParse.format(cal.getTime()).equals(dateTime));
                            if (dateParse.format(cal.getTime()).equals(dateTime)) {
                                if (myDb.getCountNoti().equals("0")) {
                                    Calendar dateNow = Calendar.getInstance();
                                    dateNow.add(Calendar.SECOND, 5);

                                    final int _id = (int) System.currentTimeMillis();

                                    Intent intent = new Intent(getBaseContext(), ReceiveBoardCast.class);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, dateNow.getTimeInMillis(), pendingIntent);

                                    myDb.setCountOne();
                                    System.out.println(" set one ");

                                } else {
                                    Calendar calStop = Calendar.getInstance();
                                    if (count == response.body().size() - 1) {
                                        //calStop.setTime(response.body().get(count).getAppDate());

                                    } else {
                                        calStop.setTime(response.body().get(count + 1).getAppDate());
                                        calStop.add(Calendar.DATE, -3);

                                        final int _id = (int) System.currentTimeMillis();

                                        Intent intent = new Intent(getBaseContext(), StopServerReceiver.class);
                                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        alarmManager.set(AlarmManager.RTC_WAKEUP, calStop.getTimeInMillis(), pendingIntent);

                                        System.out.println(" set stop ");
                                    }

                                    stopService(new Intent(NotiService.this, NotiService.class));

                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Appointment>> call, Throwable t) {
                    System.out.println("failure get appointment to noti " + t.toString());
                }
            });
        } else {
            System.out.println("id empty");
        }
    }

}