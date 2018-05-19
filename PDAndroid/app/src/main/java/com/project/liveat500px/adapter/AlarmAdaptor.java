package com.project.liveat500px.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.project.liveat500px.R;
import com.project.liveat500px.Service.AlarmReciever;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baboat on 20/10/2560.
 */

public class AlarmAdaptor extends BaseAdapter {

    Context mContext;
    ArrayList<String> alarmRound;
    ArrayList<String> alarmTime;
    ArrayList<String> alarmToday;
    ToggleButton alarmToggle;

    public AlarmAdaptor(Context context, ArrayList<String> alarmRound, ArrayList<String> alarmTime, ArrayList<String> alarmToday ) {
        this.mContext = context;
        this.alarmRound = alarmRound;
        this.alarmTime = alarmTime;
        this.alarmToday = alarmToday;
    }

    public int getCount() {
        return alarmTime.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.fragmentalarm, parent, false);
//
//        CircleImageView date = (CircleImageView) row.findViewById(R.id.FoodCircle);
//        date.setImageResource(icons.get(position));
//        date.setBackgroundResource(backgrounds.get(position));

        TextView alarmText1 = (TextView) row.findViewById(R.id.alarmText1);
        alarmText1.setText(alarmRound.get(position));

        TextView alarmText2 = (TextView) row.findViewById(R.id.alarmText2);
        alarmText2.setText(alarmTime.get(position)+" น.");
        TextView alarmText3 = (TextView) row.findViewById(R.id.alarmText3);
        alarmText3.setText(alarmToday.get(position));
        alarmToggle = (ToggleButton) row.findViewById(R.id.alarmToggle);
        alarmToggle.setChecked(true);
        alarmToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();

                if (on) {
                    Calendar cal = Calendar.getInstance();
                    String hour = alarmTime.get(position).substring(0,2);
                    String minute = alarmTime.get(position).substring(3,5);
                    int h = Integer.parseInt(hour);
                    int m = Integer.parseInt(minute);
                    Toast.makeText(mContext,"เปิดการแจ้งเตือนแล้ว",Toast.LENGTH_SHORT).show();

                    if(alarmToday.get(position).toString().equals("    พรุ่งนี้")){
                        cal.add(Calendar.DATE,+1);
                        cal.set(Calendar.HOUR_OF_DAY, h);
                        cal.set(Calendar.MINUTE, m);
                        setAlarm(cal,alarmRound.get(position));
                    }

                } else {
                    stopAlarm(alarmRound.get(position));
                }
            }
        });

        return row;
    }

    public void setAlarm(Calendar targetCal , String round){

        final int _id = Integer.parseInt(round);
        Intent intent = new Intent(mContext, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    public void stopAlarm(String round){
        final int _id = Integer.parseInt(round);
        Intent intent = new Intent(mContext, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, _id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(mContext,"ปิดการแจ้งเตือนแล้ว",Toast.LENGTH_SHORT).show();
        alarmManager.cancel(pendingIntent);
    }
}
