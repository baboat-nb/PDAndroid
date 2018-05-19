package com.project.liveat500px.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.R;
import com.project.liveat500px.Service.AlarmReciever;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.adapter.AlarmAdaptor;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


public class AlarmFragment extends Fragment {
    Context context;
    DataBaseHelper mydb;
    FancyButton btnAlarmPlus;
    private Fragment fragment;
    public static ArrayList<String> listValue;
    private ListView listAlarm;
    List<Integer> hourIn , minuteIn ,round;
    SimpleDateFormat timeFormat;
    String day;
    private Bundle bundle;
    ArrayList<String> falarmRound = null;
    ArrayList<String> falarmTime = null;
    ArrayList<String> falarmToday = null;
    TextView alarmHistory,alarmCreate,alarmCalendar;
    AlarmAdaptor alarmAdaptor;


    public AlarmFragment() {
        super();
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
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarmHistory = (TextView) rootView.findViewById(R.id.alarmHistory);
        alarmCreate = (TextView) rootView.findViewById(R.id.alarmCreate);
        alarmCalendar = (TextView) rootView.findViewById(R.id.alarmCalendar);


        alarmHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotiHistoryFragment fragment = new NotiHistoryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });

        alarmCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertFragment fragment = new AlertFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });

        alarmCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmFragment fragment = new AlarmFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });

        btnAlarmPlus = (FancyButton) rootView.findViewById(R.id.btnAlarmPlus);
        listAlarm = (ListView)rootView.findViewById(R.id.listView1);
        listValue = new ArrayList<String>();
        redirect();
        setTimeAlarm();
        clickView();
        initInstances(rootView, savedInstanceState);
        return rootView;

    }
    public void redirect(){
        btnAlarmPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listValue.size() ==7){
                    Toast.makeText(getActivity(),"ไม่สามารถสร้างการแจ้งเตือนได้เกิน 7 รอบ",Toast.LENGTH_SHORT).show();
                }else{
                    fragment = new SetAlarmFragment();
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                }

            }
        });
    }
    public void setTimeAlarm() {
        hourIn = mydb.getHourAlarm();
        round = mydb.getRoundAlarm();
        minuteIn = mydb.getMinuteAlarm();
        timeFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        falarmRound = new ArrayList<String>();
        falarmTime = new ArrayList<String>();
        falarmToday = new ArrayList<String>();

        if(hourIn != null){
            for (int i = 0; i < hourIn.size(); i++) {
                if(hourIn.get(i) != 0){
                    cal.set(Calendar.HOUR_OF_DAY, hourIn.get(i));
                    cal.set(Calendar.MINUTE, minuteIn.get(i));

                    String time = timeFormat.format(cal.getTime());
                    System.out.println(hourIn.get(i)+"HH"+round.get(i)+"RR"+minuteIn.get(i));
                    if(now.getTime().after(cal.getTime())){
                        day = "    พรุ่งนี้";
                    }else{
                        day = "    วันนี้";
                    }
                    listValue.add("รอบที่  "+round.get(i)+"    เวลา     " +time + day);

                    falarmRound.add(round.get(i).toString());
                    falarmTime.add(time);
                    falarmToday.add(day);
                }
            }
        }

        alarmAdaptor = new AlarmAdaptor(getActivity(), falarmRound,falarmTime,falarmToday);
        listAlarm.setAdapter(alarmAdaptor);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listValue);
//        listAlarm.setAdapter(adapter);

    }

    public void clickView(){
        listAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle = new Bundle();
                fragment = new SetAlarmFragment();

                bundle.putInt("hour",hourIn.get(position));
                bundle.putInt("minute",minuteIn.get(position));
                bundle.putInt("round" ,round.get(position) );
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
            }
        });
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,listValue);
//        listAlarm.setAdapter(adapter);
//        System.out.println("onresume alarm");
//    }
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