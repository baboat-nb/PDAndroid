package com.project.liveat500px.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Appointment;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;
import com.project.liveat500px.manager.http.HttpTimeResponse;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class AlertFragment extends Fragment {
    MaterialCalendarView calendarView;
    Context context;
    DataBaseHelper mydb;
    SimpleDateFormat dateParse;
    TextView alertHistory,alertCalendar,alertCreate;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public AlertFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static AlertFragment newInstance() {
        AlertFragment fragment = new AlertFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_alert, container, false);
        alertHistory = (TextView) rootView.findViewById(R.id.alertHistory);
        alertCalendar = (TextView) rootView.findViewById(R.id.alertCalendar);
        alertCreate = (TextView) rootView.findViewById(R.id.alertCreate);
        calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarAppointment);

        alertHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotiHistoryFragment fragment = new NotiHistoryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });

        alertCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmFragment fragment = new AlarmFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });
        displayCalendar();

        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    public void displayCalendar() {
        String id = mydb.getAllData();
        System.out.println("id  alert : " + id);
        Call<List<Appointment>> call = HttpManager.getInstance().getService().findAppointment(id);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response != null) {
                    final List<Appointment> appList = response.body();

                    if (appList.isEmpty()) {
                        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                            @Override
                            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                                calendarView.setDateSelected(date, false);
                                Toast.makeText(getActivity(), "ไม่มีการนัดหมาย", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        for (int i = 0; i < appList.size(); i++) {
                            calendarView.setDateSelected(appList.get(i).getAppDate(), true);
                        }

                        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                            @Override
                            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                                boolean check = false;
                                dateParse = new SimpleDateFormat("yyyy-MM-dd");
                                for (int i = 0; i < appList.size(); i++) {
                                    calendarView.setDateSelected(appList.get(i).getAppDate(), true);
                                }
                                for (int i = 0; i < appList.size(); i++) {
                                    String dateSelect = dateParse.format(date.getDate());
                                    String dateApp = dateParse.format(appList.get(i).getAppDate());
                                    if (dateSelect.equalsIgnoreCase(dateApp)) {
                                        Toast.makeText(getActivity(), appList.get(i).getAppName() + " เวลา " + appList.get(i).getAppTime().substring(0,5) + " น.", Toast.LENGTH_SHORT).show();
                                        check = true;
                                        break;
                                    } else {
                                        check = false;

                                    }
                                }

                                if (check == false) {
                                    calendarView.setDateSelected(date, false);
                                    Toast.makeText(getActivity(), "ไม่มีการนัดหมาย", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                } else {
                    System.out.println("appoint error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                System.out.println("appointment failure " + t.toString());
            }
        });

    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //listView = (ListView) rootView.findViewById(R.id.listView);
        //listAdapter = new PhotoListAdapter();
        //listView.setAdapter(listAdapter);
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