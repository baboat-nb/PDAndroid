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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.MainActivity;
import com.project.liveat500px.manager.http.HttpTimeResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment6 extends Fragment {

    TimePicker timePickerOutSta;
    FancyButton btnSaveDiaOutSta;
    Context context;
    private TextView dateFrag6;
    private TextView roundFrag6;
    private Bundle bundle;
    private String date;
    private String round;
    private String diaId;
    private String recId;
    private int hourOutSta;
    private int minuteOutSta;
    private SimpleDateFormat parseTime;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public MainFragment6() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment6 newInstance() {
        MainFragment6 fragment = new MainFragment6();
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

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a6, container, false);

        bundle = getArguments();
        this.date = bundle.getString("date");
        this.round = bundle.getString("round");
        this.diaId = bundle.getString("diaId");
        this.recId = bundle.getString("recId");

        dateFrag6 = (TextView) rootView.findViewById(R.id.dateFrag6);
        roundFrag6 = (TextView) rootView.findViewById(R.id.roundFrag6);
        dateFrag6.setText(date);
        roundFrag6.setText(round);


        timePickerOutSta = (TimePicker) rootView.findViewById(R.id.timePickerOutSta);
        timePickerOutSta.setIs24HourView(true);

        btnSaveDiaOutSta = (FancyButton) rootView.findViewById(R.id.btnSaveDiaOutSta);

        updateDialysisOutSta();
        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    public void updateDialysisOutSta(){
        parseTime = new SimpleDateFormat("hh:mm:ss");
        btnSaveDiaOutSta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialysis dia = new Dialysis();
                if (Build.VERSION.SDK_INT < 23) {
                    hourOutSta = timePickerOutSta.getCurrentHour();
                    minuteOutSta= timePickerOutSta.getCurrentMinute();

                } else {
                    hourOutSta = timePickerOutSta.getHour();
                    minuteOutSta = timePickerOutSta.getMinute();
                }
                String timeIn = hourOutSta + ":" + minuteOutSta + ":00";
                try {
                    dia.setTimeDiaOut_start(parseTime.parse(timeIn));

                    Call<Void> call = HttpTimeResponse.getInstance().getService().updateDiaOutSta(dia, recId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response != null) {
                                Toast.makeText(getActivity(),"บันทึกการล้างไตแล้ว",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                System.out.println("create diaInsta fail"+response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("failure to diaInsta" + t.toString());
                        }
                    });
                }catch (ParseException e){
                    System.out.println("parse error frag6" + e.toString());
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
