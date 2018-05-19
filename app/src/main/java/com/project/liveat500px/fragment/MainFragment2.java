package com.project.liveat500px.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.EditProActivity;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.sql.SQLOutput;
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
public class MainFragment2 extends Fragment {


    private DataBaseHelper myDb;
    private DatePicker roundDatePicker;
    private com.shawnlin.numberpicker.NumberPicker roundNumberPicker;
    private FancyButton roundBtnSave;
    private SimpleDateFormat parseDate;
    private String id;
    private Context context;
    private int yearRec, monthRec, dayRec;
    private Record record;
    private Fragment fragment;
    private Bundle bundle;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public MainFragment2() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment2 newInstance() {
        MainFragment2 fragment = new MainFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        init(savedInstanceState);
        myDb = new DataBaseHelper(context);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_a2, container, false);

        roundDatePicker = (DatePicker) rootView.findViewById(R.id.roundDatePicker);
        roundNumberPicker = (com.shawnlin.numberpicker.NumberPicker) rootView.findViewById(R.id.roundNumberPicker);
        roundBtnSave = (FancyButton) rootView.findViewById(R.id.roundBtnSave);

        initInstances(rootView, savedInstanceState);

        saveRecord();

        return rootView;

    }
    private void saveRecord() {
        bundle = new Bundle();
        fragment = new MainFragment4();
        id = myDb.getAllData();

        parseDate = new SimpleDateFormat("yyyy-MM-dd");

        roundBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    record = new Record();
                    dayRec = roundDatePicker.getDayOfMonth();
                    monthRec = roundDatePicker.getMonth() + 1;
                    yearRec = roundDatePicker.getYear();
                    String date = yearRec + "-" + monthRec + "-" + dayRec;
                    String dateBundle = dayRec+"-"+monthRec+"-"+yearRec;
                    record.setRecRound(roundNumberPicker.getValue());
                    record.setRecDate(parseDate.parse(date));
                    System.out.println("date : " + parseDate.parse(date));
                    record.setPatient_patId_fk(id);

                    bundle.putString("date",dateBundle);
                    bundle.putString("round" ,roundNumberPicker.getValue()+"" );
                    System.out.println("get"+bundle.getString("date"));
                    fragment.setArguments(bundle);
                    Call<Boolean> call = HttpDateResponse.getInstance().getService().createRecord(record);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response != null) {
                                if (response.body() == true) {
                                    getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
                                } else {
                                    Toast.makeText(getActivity(),"มีวันที่และรอบนี้แล้ว",Toast.LENGTH_SHORT).show();
                                }
                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            System.out.println("failure check round "+t.toString());
                        }
                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("error record" + e.getMessage());
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
