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
 * Created by Fame on 9/24/2017.
 */

public class MainFragment7 extends Fragment{

    Context context;
    TimePicker timePickerOutEnd;
    FancyButton btnSaveDiaOutEnd;
    MaterialSpinner spinnerLiquid;
    private TextView dateFrag7;
    private TextView roundFrag7;
    EditText volOut;
    EditText editUrinateEndOut;
    private Bundle bundle;
    private String date;
    private String round;
    private String diaId;
    private String recId;
    private int hourOutEnd;
    private int minuteOutEnd;
    private SimpleDateFormat parseTime;

    public MainFragment7() {
        super();
    }

    public static MainFragment7 newInstance() {
        MainFragment7 fragment = new MainFragment7();
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
        View rootView = inflater.inflate(R.layout.fragment_a7, container, false);

        bundle = getArguments();
        this.date = bundle.getString("date");
        this.round = bundle.getString("round");
        this.diaId = bundle.getString("diaId");
        this.recId = bundle.getString("recId");

        dateFrag7 = (TextView) rootView.findViewById(R.id.dateFrag7);
        roundFrag7 = (TextView) rootView.findViewById(R.id.roundFrag7);
        dateFrag7.setText(date);
        roundFrag7.setText(round);

        volOut = (EditText) rootView.findViewById(R.id.volOut);
        editUrinateEndOut = (EditText) rootView.findViewById(R.id.editUrinateEndOut);
        timePickerOutEnd = (TimePicker) rootView.findViewById(R.id.timePickerOutaEnd);
        timePickerOutEnd.setIs24HourView(true);
        spinnerLiquid = (MaterialSpinner) rootView.findViewById(R.id.spinnerLiquid);
        spinnerLiquid.setItems("เหลืองใส", "ขาวขุ่น");

        btnSaveDiaOutEnd = (FancyButton) rootView.findViewById(R.id.btnSaveDiaOutEnd);

        updateDialysisOutEnd();
        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    public void updateDialysisOutEnd(){
        parseTime = new SimpleDateFormat("hh:mm:ss");
        btnSaveDiaOutEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialysis dia = new Dialysis();
                if (Build.VERSION.SDK_INT < 23) {
                    hourOutEnd = timePickerOutEnd.getCurrentHour();
                    minuteOutEnd= timePickerOutEnd.getCurrentMinute();

                } else {
                    hourOutEnd = timePickerOutEnd.getHour();
                    minuteOutEnd = timePickerOutEnd.getMinute();
                }
                String timeOut = hourOutEnd + ":" + minuteOutEnd + ":00";
                try{
                    dia.setTimeDiaOut_end(parseTime.parse(timeOut));
                    dia.setVolDiaOut(Integer.parseInt(volOut.getText().toString()));
                    dia.setUrinate(Integer.parseInt(editUrinateEndOut.getText().toString()));
                    dia.setDesDiaLiquid(spinnerLiquid.getText().toString());
                    System.out.println(spinnerLiquid.getText().toString());
                    Call<Void> call = HttpTimeResponse.getInstance().getService().updateDiaOutEnd(dia, recId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response != null) {
                                Toast.makeText(getActivity(),"บันทึกการล้างไตทั้งหมดสำเร็จแล้ว",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                System.out.println("create diaInsta fail"+response.errorBody());
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                }catch (ParseException e){
                    System.out.println("parse error fragment 7 : "+e.toString());
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
