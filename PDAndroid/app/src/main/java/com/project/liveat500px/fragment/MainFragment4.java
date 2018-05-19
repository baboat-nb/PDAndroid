package com.project.liveat500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.MainActivity;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;
import com.project.liveat500px.manager.http.HttpTimeResponse;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment4 extends Fragment {
    DataBaseHelper myDb;
    TimePicker timePickerIn;

    FancyButton btnSaveDia;
    MaterialSpinner spinnerIntensity;
    private EditText volIn;
    private TextView dateFrag4;
    private TextView roundFrag4;
    //ListView listView;
    //PhotoListAdapter listAdapter;
    private int hourIn, minuteIn;
    private SimpleDateFormat parseTime;
    private SimpleDateFormat parseDate;
    private SimpleDateFormat parseDateTofind;
    private Bundle bundle;
    private String date;
    private String round;
    private int roundInt;
    private Date dateDate;
    private String recId;
    private Context context;


    public MainFragment4() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment4 newInstance() {
        MainFragment4 fragment = new MainFragment4();
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
        myDb = new DataBaseHelper(context);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a4, container, false);

        bundle = getArguments();
        date = bundle.getString("date");
        round = bundle.getString("round");

        dateFrag4 = (TextView) rootView.findViewById(R.id.dateFrag4);
        roundFrag4 = (TextView) rootView.findViewById(R.id.roundFrag4);
        dateFrag4.setText(date);
        roundFrag4.setText(round);

        timePickerIn = (TimePicker) rootView.findViewById(R.id.timePickerIn);
        timePickerIn.setIs24HourView(true);
        volIn = (EditText) rootView.findViewById(R.id.volIn);
        spinnerIntensity = (MaterialSpinner) rootView.findViewById(R.id.spinnerIntensity);
        spinnerIntensity.setItems("1.50", "2.50", "4.25");
        btnSaveDia = (FancyButton) rootView.findViewById(R.id.btnSaveDia);
        dateFrag4 = (TextView) rootView.findViewById(R.id.dateFrag4);
        roundFrag4 = (TextView) rootView.findViewById(R.id.roundFrag4);

        findRecId();
        saveDialysis();

        initInstances(rootView, savedInstanceState);

        return rootView;

    }
    public String parseFormatDate(String oldDate){
        String newDate = null;
        parseDate = new SimpleDateFormat("yyyy-MM-dd");
        parseDateTofind = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = parseDateTofind.parse(oldDate);
            newDate = parseDate.format(date);
            System.out.println("newDate " +newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public String findRecId() {
        String id = myDb.getAllData();
        String date = parseFormatDate(dateFrag4.getText().toString());
        Call<String> call = HttpManager.getInstance().getService().findRecId(date, Integer.parseInt(roundFrag4.getText().toString()), id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    recId = response.body();
                } else {
                    System.out.println("error recid " + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("failure " + t.toString());
            }
        });
        return recId;
    }

    public void saveDialysis() {
        parseTime = new SimpleDateFormat("hh:mm:ss");
        parseDate = new SimpleDateFormat("yyyy-MM-dd");


        btnSaveDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recId = findRecId();
                Dialysis dia = new Dialysis();
                if (Build.VERSION.SDK_INT < 23) {
                    hourIn = timePickerIn.getCurrentHour();
                    minuteIn = timePickerIn.getCurrentMinute();

                } else {
                    hourIn = timePickerIn.getHour();
                    minuteIn = timePickerIn.getMinute();
                }
                String timeIn = hourIn + ":" + minuteIn+":00";
                try {
                    int volInParse = Integer.parseInt(volIn.getText().toString());
                    double intensity = Double.parseDouble(spinnerIntensity.getText().toString());

                    dia.setTimeDiaIn_start(parseTime.parse(timeIn));
                    dia.setIntensity(intensity);
                    dia.setVolDiaIn(volInParse);
                    dia.setRecord_recId_fk(Integer.parseInt(recId));

                    Call<Void> dialysisRes = HttpTimeResponse.getInstance().getService().createDialysis(dia);
                    dialysisRes.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response != null) {
                                Toast.makeText(getActivity(),"บันทึกการล้างไตแล้ว",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                System.out.println("error create dia " + response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("error failure dia " + t.toString());
                        }
                    });

                } catch (ParseException e) {
                    System.out.println("dialysis error : " + e.toString());
                    System.out.println("dialysis error : " + e.getCause());
                }
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
