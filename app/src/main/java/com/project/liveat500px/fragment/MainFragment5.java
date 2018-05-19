package com.project.liveat500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment5 extends Fragment {

    private DataBaseHelper mydb;
    TextView dateFrag5;
    TextView roundFrag5;
    FancyButton btnSaveDiaInOut;
    EditText editUrinateInOut;
    private TimePicker timePickerInOut;
    private Bundle bundle;
    private String date;
    private String round;
    private String diaId;
    private String recId;
    private int hourInEnd;
    private int minuteInEnd;
    private SimpleDateFormat parseTime;
    private Context context;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public MainFragment5() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment5 newInstance() {
        MainFragment5 fragment = new MainFragment5();
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
        View rootView = inflater.inflate(R.layout.fragment_a5_new, container, false);

        bundle = getArguments();
        this.date = bundle.getString("date");
        this.round = bundle.getString("round");
        this.diaId = bundle.getString("diaId");
        this.recId = bundle.getString("recId");
        dateFrag5 = (TextView) rootView.findViewById(R.id.dateFrag5);
        roundFrag5 = (TextView) rootView.findViewById(R.id.roundFrag5);
        dateFrag5.setText(date);
        roundFrag5.setText(round);
        editUrinateInOut = (EditText) rootView.findViewById(R.id.editUrinateInOut);
        timePickerInOut = (TimePicker) rootView.findViewById(R.id.timePickerInOut);
        btnSaveDiaInOut = (FancyButton) rootView.findViewById(R.id.btnSaveDiaInOut);
        timePickerInOut.setIs24HourView(true);
        updateDiaInEnd();
        initInstances(rootView, savedInstanceState);

        return rootView;

    }

    public void updateDiaInEnd() {
        parseTime = new SimpleDateFormat("hh:mm:ss");
        String id = mydb.getAllData();

        btnSaveDiaInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialysis dia = new Dialysis();
                if (Build.VERSION.SDK_INT < 23) {
                    hourInEnd = timePickerInOut.getCurrentHour();
                    minuteInEnd = timePickerInOut.getCurrentMinute();

                } else {
                    hourInEnd = timePickerInOut.getHour();
                    minuteInEnd = timePickerInOut.getMinute();
                }
                String timeIn = hourInEnd + ":" + minuteInEnd + ":00";


                try {
                    dia.setTimeDiaIn_end(parseTime.parse(timeIn));
                    dia.setUrinate(Integer.parseInt(editUrinateInOut.getText().toString()));

                    Call<Void> call = HttpTimeResponse.getInstance().getService().updateDiaInEnd(dia ,recId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response != null) {
                                Toast.makeText(getActivity(),"บันทึกการล้างไตทั้งแล้ว",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                System.out.println("create diaEnd fail"+response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } catch (ParseException e) {
                    System.out.println("parse error fragment 5 "+e.toString());
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