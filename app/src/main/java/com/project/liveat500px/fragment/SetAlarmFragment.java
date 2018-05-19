package com.project.liveat500px.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.R;
import com.project.liveat500px.Service.AlarmReciever;
import com.project.liveat500px.activity.DataBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;


public class SetAlarmFragment extends Fragment {
    Context context;
    DataBaseHelper mydb;
    private Fragment fragment;
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    MaterialSpinner spinnerRoundAlarm;
    private Bundle bundle;
    int hour , minute ,round , hourIn , minuteIn;



    public SetAlarmFragment() {
        // Required empty public constructor
    }
    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        this.context = getActivity();
        init(savedInstanceState);
        mydb = new DataBaseHelper(context);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_set_alarm, container, false);

        bundle = getArguments();

        spinnerRoundAlarm = (MaterialSpinner) rootView.findViewById(R.id.spinnerRoundAlarm);
        spinnerRoundAlarm.setItems("1", "2", "3","4", "5", "6","7");
        spinnerRoundAlarm.setDropdownHeight(300);
        myTimePicker = (TimePicker) rootView.findViewById(R.id.timepickerSet);
        myTimePicker.setIs24HourView(true);
        buttonstartSetDialog = (Button)rootView.findViewById(R.id.startSetDialog);

        if(bundle != null){
            hour = bundle.getInt("hour");
            minute = bundle.getInt("minute");
            round = bundle.getInt("round");

            myTimePicker.setCurrentHour(hour);
            myTimePicker.setCurrentMinute(minute);
            spinnerRoundAlarm.setSelectedIndex(round-1);
            spinnerRoundAlarm.setEnabled(false);
        }
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);

                if (Build.VERSION.SDK_INT < 23) {
                    hourIn = myTimePicker.getCurrentHour();
                    minuteIn = myTimePicker.getCurrentMinute();

                } else {
                    hourIn = myTimePicker.getHour();
                    minuteIn = myTimePicker.getMinute();
                }
                String round =spinnerRoundAlarm.getText().toString();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourIn);
                cal.set(Calendar.MINUTE, minuteIn);
                cal.add(Calendar.SECOND,+1);

                Calendar now = Calendar.getInstance();

                if(bundle == null){
                    boolean check =mydb.checkRound(round);
                    if(check == false){
                        mydb.insertAlarm(hourIn,minuteIn,round);
                        if(now.getTime().after(cal.getTime())){
                            cal.add(Calendar.DATE,+1);
                            System.out.println("date tomorrow "+ cal.getTime());
                        }

                        System.out.println("cal alarm"+cal.getTime());
                        setAlarm(cal,round);

                        fragment = new AlarmFragment();
                        getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
                    }else{
                        Toast.makeText(getActivity(),"การแจ้งเตือนมีรอบที่ "+round +"แล้ว",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    mydb.updateTimeAlert(hourIn,minuteIn,round);

                    if(now.getTime().after(cal.getTime())){
                        cal.add(Calendar.DATE,+1);
                        System.out.println("date tomorrow "+ cal.getTime());
                    }

                    System.out.println("cal alarm"+cal.getTime());
                    stopAlarm(round);
                    setAlarm(cal,round);

                    Toast.makeText(getActivity(),"แก้ไขการแจ้งเตือนสำเร็จแล้ว",Toast.LENGTH_SHORT).show();

                    fragment = new AlarmFragment();
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
                }



            }
        });

        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    public void setAlarm(Calendar targetCal , String round){

        final int _id = Integer.parseInt(round);
        System.out.println("id alarm "+_id);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        System.out.println("alarm setTime start");

    }

    public void stopAlarm(String round){
        final int _id = Integer.parseInt(round);
        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), _id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        System.out.println("alarm cancel start");
        alarmManager.cancel(pendingIntent);
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //listView = (ListView) rootView.findViewById(R.id.listView);
        //listAdapter = new PhotoListAdapter();
        //listView.setAdapter(listAdapter);
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }
}