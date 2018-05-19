package com.project.liveat500px.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.activity.RoundActivity;
import com.project.liveat500px.manager.http.HttpTimeResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.key;
import static android.content.Context.MODE_PRIVATE;
import static com.project.liveat500px.activity.RoundActivity.MY_PREFS_NAME;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class DataFragment extends Fragment {

    MaterialSpinner spinnerDate;
    MaterialSpinner spinnerMonth;
    MaterialSpinner spinnerYear;
    LinearLayout txtround1, txtround2, txtround3, txtround4, txtround5, txtround6, txtround7;
    FancyButton btnSearch;
    DataBaseHelper myDb;
    String date;
    String month;
    String year;
    String finaldate;
    TextView txtProfit;
    TextView txtUrinate;
    TextView txtintent1, txtintent2, txtintent3, txtintent4, txtintent5, txtintent6, txtintent7;
    TextView txtProfit1, txtProfit2, txtProfit3, txtProfit4, txtProfit5, txtProfit6, txtProfit7;
    int checkCase;
    String nameShared;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    int count;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public DataFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        myDb = new DataBaseHelper(getActivity());

        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("edit", "nochange");
        nameShared = prefs.getString("edit", "No name defined");
        count = 0;
//        final Handler refreshHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                if(count+ prefs.getInt("count",0)!=0) {
//                    if (nameShared.equals("change")) {
//                        btnSearch.performClick();
//                        editor.putString("edit", "nochange");
//                        nameShared = prefs.getString("edit", "No name defined");
//                        count = count-prefs.getInt("count",0)-prefs.getInt("count",0);
//                    }
//
//                }
//
//                refreshHandler.postDelayed(this, 3 * 1000);
//            }
//        };
//        refreshHandler.postDelayed(runnable, 3 * 1000);

//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(800);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try{
//                                    if(getArguments().getInt("case") != 0) {
//                                        int strtext = getArguments().getInt("case");
//                                        System.out.println("BOAT"+strtext);
//                                        checkCase = strtext;
//                                    }
//                                }catch (Exception e){
//                                    System.out.println(e.toString()+e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//
//        t.start();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);

//        int strtext = getArguments().getInt("case");
//        System.out.println("BOAT"+strtext);

        txtround1 = (LinearLayout) rootView.findViewById(R.id.txtround1);
        txtround2 = (LinearLayout) rootView.findViewById(R.id.txtround2);
        txtround3 = (LinearLayout) rootView.findViewById(R.id.txtround3);
        txtround4 = (LinearLayout) rootView.findViewById(R.id.txtround4);
        txtround5 = (LinearLayout) rootView.findViewById(R.id.txtround5);
        txtround6 = (LinearLayout) rootView.findViewById(R.id.txtround6);
        txtround7 = (LinearLayout) rootView.findViewById(R.id.txtround7);
        txtProfit = (TextView) rootView.findViewById(R.id.txtProfit);
        txtUrinate = (TextView) rootView.findViewById(R.id.txtUrinate);
        txtintent1 = (TextView) rootView.findViewById(R.id.txtintent1);
        txtintent2 = (TextView) rootView.findViewById(R.id.txtintent2);
        txtintent3 = (TextView) rootView.findViewById(R.id.txtintent3);
        txtintent4 = (TextView) rootView.findViewById(R.id.txtintent4);
        txtintent5 = (TextView) rootView.findViewById(R.id.txtintent5);
        txtintent6 = (TextView) rootView.findViewById(R.id.txtintent6);
        txtintent7 = (TextView) rootView.findViewById(R.id.txtintent7);
        txtProfit1 = (TextView) rootView.findViewById(R.id.txtProfit1);
        txtProfit2 = (TextView) rootView.findViewById(R.id.txtProfit2);
        txtProfit3 = (TextView) rootView.findViewById(R.id.txtProfit3);
        txtProfit4 = (TextView) rootView.findViewById(R.id.txtProfit4);
        txtProfit5 = (TextView) rootView.findViewById(R.id.txtProfit5);
        txtProfit6 = (TextView) rootView.findViewById(R.id.txtProfit6);
        txtProfit7 = (TextView) rootView.findViewById(R.id.txtProfit7);

        Date realDate = new Date();
        DateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");

        String RealDMY = formatdate.format(realDate);
        String stringRealYear = RealDMY.substring(0, 4);
        String stringRealMonth = RealDMY.substring(5, 7);
        String stringRealDate = RealDMY.substring(8, 10);

//        spinnerDate.setItems(stringRealDate);
//        spinnerMonth.setItems(stringRealMonth);
//        spinnerYear.setItems(stringRealYear);

        Date dk = new Date();
        spinnerDate = (MaterialSpinner) rootView.findViewById(R.id.spinnerDate);
        spinnerDate.setItems(stringRealDate);
        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
        spinnerDate.setDropdownHeight(300);
        String thisDay = dk.toString().substring(8, 10);
        switch (thisDay) {
            case "01":
                spinnerDate.setSelectedIndex(0);
                date = spinnerDate.getItems().get(0).toString();
                break;
            case "02":
                spinnerDate.setSelectedIndex(1);
                date = spinnerDate.getItems().get(1).toString();
                break;
            case "03":
                spinnerDate.setSelectedIndex(2);
                date = spinnerDate.getItems().get(2).toString();
                break;
            case "04":
                spinnerDate.setSelectedIndex(3);
                date = spinnerDate.getItems().get(3).toString();
                break;
            case "05":
                spinnerDate.setSelectedIndex(4);
                date = spinnerDate.getItems().get(4).toString();
                break;
            case "06":
                spinnerDate.setSelectedIndex(5);
                date = spinnerDate.getItems().get(5).toString();
                break;
            case "07":
                spinnerDate.setSelectedIndex(6);
                date = spinnerDate.getItems().get(6).toString();
                break;
            case "08":
                spinnerDate.setSelectedIndex(7);
                date = spinnerDate.getItems().get(7).toString();
                break;
            case "09":
                spinnerDate.setSelectedIndex(8);
                date = spinnerDate.getItems().get(8).toString();
                break;
            case "10":
                spinnerDate.setSelectedIndex(9);
                date = spinnerDate.getItems().get(9).toString();
                break;
            case "11":
                spinnerDate.setSelectedIndex(10);
                date = spinnerDate.getItems().get(10).toString();
                break;
            case "12":
                spinnerDate.setSelectedIndex(11);
                date = spinnerDate.getItems().get(11).toString();
                break;
            case "13":
                spinnerDate.setSelectedIndex(12);
                date = spinnerDate.getItems().get(12).toString();
                break;
            case "14":
                spinnerDate.setSelectedIndex(13);
                date = spinnerDate.getItems().get(13).toString();
                break;
            case "15":
                spinnerDate.setSelectedIndex(14);
                date = spinnerDate.getItems().get(14).toString();
                break;
            case "16":
                spinnerDate.setSelectedIndex(15);
                date = spinnerDate.getItems().get(15).toString();
                break;
            case "17":
                spinnerDate.setSelectedIndex(16);
                date = spinnerDate.getItems().get(16).toString();
                break;
            case "18":
                spinnerDate.setSelectedIndex(17);
                date = spinnerDate.getItems().get(17).toString();
                break;
            case "19":
                spinnerDate.setSelectedIndex(18);
                date = spinnerDate.getItems().get(18).toString();
                break;
            case "20":
                spinnerDate.setSelectedIndex(19);
                date = spinnerDate.getItems().get(19).toString();
                break;
            case "21":
                spinnerDate.setSelectedIndex(20);
                date = spinnerDate.getItems().get(20).toString();
                break;
            case "22":
                spinnerDate.setSelectedIndex(21);
                date = spinnerDate.getItems().get(21).toString();
                break;
            case "23":
                spinnerDate.setSelectedIndex(22);
                date = spinnerDate.getItems().get(22).toString();
                break;
            case "24":
                spinnerDate.setSelectedIndex(23);
                date = spinnerDate.getItems().get(23).toString();
                break;
            case "25":
                spinnerDate.setSelectedIndex(24);
                date = spinnerDate.getItems().get(24).toString();
                break;
            case "26":
                spinnerDate.setSelectedIndex(25);
                date = spinnerDate.getItems().get(25).toString();
                break;
            case "27":
                spinnerDate.setSelectedIndex(26);
                date = spinnerDate.getItems().get(26).toString();
                break;
            case "28":
                spinnerDate.setSelectedIndex(27);
                date = spinnerDate.getItems().get(27).toString();
                break;
            case "29":
                spinnerDate.setSelectedIndex(28);
                date = spinnerDate.getItems().get(28).toString();
                break;
            case "30":
                spinnerDate.setSelectedIndex(29);
                date = spinnerDate.getItems().get(29).toString();
                break;
            case "31":
                spinnerDate.setSelectedIndex(30);
                date = spinnerDate.getItems().get(30).toString();
                break;
        }

        spinnerMonth = (MaterialSpinner) rootView.findViewById(R.id.spinnerMonth);
        String thisMonth = dk.toString().substring(4, 7);
        spinnerMonth.setItems("มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฏาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม");
        switch (thisMonth) {
            case "Jan":
                spinnerMonth.setSelectedIndex(0);
                month = spinnerMonth.getItems().get(0).toString();
                break;
            case "Feb":
                spinnerMonth.setSelectedIndex(1);
                month = spinnerMonth.getItems().get(1).toString();
                break;
            case "Mar":
                spinnerMonth.setSelectedIndex(2);
                month = spinnerMonth.getItems().get(2).toString();
                break;
            case "Apr":
                spinnerMonth.setSelectedIndex(3);
                month = spinnerMonth.getItems().get(3).toString();
                break;
            case "May":
                spinnerMonth.setSelectedIndex(4);
                month = spinnerMonth.getItems().get(4).toString();
                break;
            case "Jun":
                spinnerMonth.setSelectedIndex(5);
                month = spinnerMonth.getItems().get(5).toString();
                break;
            case "Jul":
                spinnerMonth.setSelectedIndex(6);
                month = spinnerMonth.getItems().get(6).toString();
                break;
            case "Aug":
                spinnerMonth.setSelectedIndex(7);
                month = spinnerMonth.getItems().get(7).toString();
                break;
            case "Sep":
                spinnerMonth.setSelectedIndex(8);
                month = spinnerMonth.getItems().get(8).toString();
                break;
            case "Oct":
                spinnerMonth.setSelectedIndex(9);
                month = spinnerMonth.getItems().get(9).toString();
                break;
            case "Nov":
                spinnerMonth.setSelectedIndex(10);
                month = spinnerMonth.getItems().get(10).toString();
                break;
            case "Dec":
                spinnerMonth.setSelectedIndex(11);
                month = spinnerMonth.getItems().get(11).toString();
                break;
        }
        spinnerMonth.setDropdownHeight(300);

        switch (month) {
            case "มกราคม":
                month = "01";
                break;
            case "กุมภาพันธ์":
                month = "02";
                break;
            case "มีนาคม":
                month = "03";
                break;
            case "เมษายน":
                month = "04";
                break;
            case "พฤษภาคม":
                month = "05";
                break;
            case "มิถุนายน":
                month = "06";
                break;
            case "กรกฏาคม":
                month = "07";
                break;
            case "สิงหาคม":
                month = "08";
                break;
            case "กันยายน":
                month = "09";
                break;
            case "ตุลาคม":
                month = "10";
                break;
            case "พฤศจิกายน":
                month = "11";
                break;
            case "ธันวาคม":
                month = "12";
                break;
        }


        spinnerYear = (MaterialSpinner) rootView.findViewById(R.id.spinnerYear);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2000; i--) {
            years.add(Integer.toString(i));
        }
        spinnerYear.setItems(years);
        spinnerYear.setDropdownHeight(300);
        year = spinnerYear.getItems().get(0).toString();

        spinnerDate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                date = item.toString();
            }
        });

        spinnerMonth.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                month = item.toString();
                switch (month) {
                    case "มกราคม":
                        month = "01";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "กุมภาพันธ์":
                        month = "02";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "มีนาคม":
                        month = "03";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "เมษายน":
                        month = "04";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "พฤษภาคม":
                        month = "05";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "มิถุนายน":
                        month = "06";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "กรกฏาคม":
                        month = "07";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "สิงหาคม":
                        month = "08";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "กันยายน":
                        month = "09";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "ตุลาคม":
                        month = "10";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "พฤศจิกายน":
                        month = "11";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
                        spinnerDate.setDropdownHeight(300);
                        break;
                    case "ธันวาคม":
                        month = "12";
                        spinnerDate.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
                        spinnerDate.setDropdownHeight(300);
                        break;
                }
            }
        });

        spinnerYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                year = item.toString();
            }
        });


        btnSearch = (FancyButton) rootView.findViewById(R.id.btnSearch);
        btnSearch.setCustomTextFont("fonts/Kanit-Regular.ttf");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myDb.getAllData();
                String startDateString = year + "-" + month + "-" + date;
                Date startDate = null;
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    startDate = df.parse(startDateString);
                    finaldate = df.format(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Call<List<Dialysis>> call = HttpTimeResponse.getInstance().getService().findAll(finaldate, id);
                call.enqueue(new Callback<List<Dialysis>>() {
                    @Override
                    public void onResponse(Call<List<Dialysis>> call, Response<List<Dialysis>> response) {
                        try {
                            final List<Dialysis> dialysis = response.body();
                            String profit = dialysis.get(0).getProfit() + "";
                            txtProfit.setText(profit);
                            String urinate = dialysis.get(0).getUrinate() + "";
                            txtUrinate.setText(urinate);


                            int size = dialysis.size();
                            switch (dialysis.size()) {
                                case 1:
                                    System.out.println("case1");
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.INVISIBLE);
                                    txtround3.setVisibility(View.INVISIBLE);
                                    txtround4.setVisibility(View.INVISIBLE);
                                    txtround5.setVisibility(View.INVISIBLE);
                                    txtround6.setVisibility(View.INVISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    String profit1 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit1);
                                    checkCase = 1;
                                    break;
                                case 2:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.INVISIBLE);
                                    txtround4.setVisibility(View.INVISIBLE);
                                    txtround5.setVisibility(View.INVISIBLE);
                                    txtround6.setVisibility(View.INVISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    String profit12 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit12);
                                    String profit22 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit22);
                                    checkCase = 2;
                                    break;
                                case 3:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.VISIBLE);
                                    txtround4.setVisibility(View.INVISIBLE);
                                    txtround5.setVisibility(View.INVISIBLE);
                                    txtround6.setVisibility(View.INVISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    txtintent3.setText("" + dialysis.get(2).getIntensity());
                                    String profit13 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit13);
                                    String profit23 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit23);
                                    String profit33 = dialysis.get(2).getProfit() + "";
                                    txtProfit3.setText(profit33);
                                    checkCase = 3;
                                    break;
                                case 4:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.VISIBLE);
                                    txtround4.setVisibility(View.VISIBLE);
                                    txtround5.setVisibility(View.INVISIBLE);
                                    txtround6.setVisibility(View.INVISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    txtintent3.setText("" + dialysis.get(2).getIntensity());
                                    txtintent4.setText("" + dialysis.get(3).getIntensity());

                                    String profit14 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit14);
                                    String profit24 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit24);
                                    String profit34 = dialysis.get(2).getProfit() + "";
                                    txtProfit3.setText(profit34);
                                    String profit44 = dialysis.get(3).getProfit() + "";
                                    txtProfit4.setText(profit44);
                                    checkCase = 4;
                                    break;
                                case 5:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.VISIBLE);
                                    txtround4.setVisibility(View.VISIBLE);
                                    txtround5.setVisibility(View.VISIBLE);
                                    txtround6.setVisibility(View.INVISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    txtintent3.setText("" + dialysis.get(2).getIntensity());
                                    txtintent4.setText("" + dialysis.get(3).getIntensity());
                                    txtintent5.setText("" + dialysis.get(4).getIntensity());
                                    String profit15 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit15);
                                    String profit25 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit25);
                                    String profit35 = dialysis.get(2).getProfit() + "";
                                    txtProfit3.setText(profit35);
                                    String profit45 = dialysis.get(3).getProfit() + "";
                                    txtProfit4.setText(profit45);
                                    String profit55 = dialysis.get(4).getProfit() + "";
                                    txtProfit5.setText(profit55);
                                    checkCase = 5;
                                    break;
                                case 6:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.VISIBLE);
                                    txtround4.setVisibility(View.VISIBLE);
                                    txtround5.setVisibility(View.VISIBLE);
                                    txtround6.setVisibility(View.VISIBLE);
                                    txtround7.setVisibility(View.INVISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    txtintent3.setText("" + dialysis.get(2).getIntensity());
                                    txtintent4.setText("" + dialysis.get(3).getIntensity());
                                    txtintent5.setText("" + dialysis.get(4).getIntensity());
                                    txtintent6.setText("" + dialysis.get(5).getIntensity());
                                    String profit16 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit16);
                                    String profit26 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit26);
                                    String profit36 = dialysis.get(2).getProfit() + "";
                                    txtProfit3.setText(profit36);
                                    String profit46 = dialysis.get(3).getProfit() + "";
                                    txtProfit4.setText(profit46);
                                    String profit56 = dialysis.get(4).getProfit() + "";
                                    txtProfit5.setText(profit56);
                                    String profit66 = dialysis.get(5).getProfit() + "";
                                    txtProfit6.setText(profit66);
                                    checkCase = 6;
                                    break;
                                case 7:
                                    txtround1.setVisibility(View.VISIBLE);
                                    txtround2.setVisibility(View.VISIBLE);
                                    txtround3.setVisibility(View.VISIBLE);
                                    txtround4.setVisibility(View.VISIBLE);
                                    txtround5.setVisibility(View.VISIBLE);
                                    txtround6.setVisibility(View.VISIBLE);
                                    txtround7.setVisibility(View.VISIBLE);
                                    txtProfit.setVisibility(View.VISIBLE);
                                    txtUrinate.setVisibility(View.VISIBLE);
                                    txtintent1.setText("" + dialysis.get(0).getIntensity());
                                    txtintent2.setText("" + dialysis.get(1).getIntensity());
                                    txtintent3.setText("" + dialysis.get(2).getIntensity());
                                    txtintent4.setText("" + dialysis.get(3).getIntensity());
                                    txtintent5.setText("" + dialysis.get(4).getIntensity());
                                    txtintent6.setText("" + dialysis.get(5).getIntensity());
                                    txtintent7.setText("" + dialysis.get(6).getIntensity());

                                    String profit17 = dialysis.get(0).getProfit() + "";
                                    txtProfit1.setText(profit17);
                                    String profit27 = dialysis.get(1).getProfit() + "";
                                    txtProfit2.setText(profit27);
                                    String profit37 = dialysis.get(2).getProfit() + "";
                                    txtProfit3.setText(profit37);
                                    String profit47 = dialysis.get(3).getProfit() + "";
                                    txtProfit4.setText(profit47);
                                    String profit57 = dialysis.get(4).getProfit() + "";
                                    txtProfit5.setText(profit57);
                                    String profit67 = dialysis.get(5).getProfit() + "";
                                    txtProfit6.setText(profit67);
                                    String profit77 = dialysis.get(6).getProfit() + "";
                                    txtProfit7.setText(profit77);
                                    checkCase = 7;
                                    break;
                            }

                            txtround1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk1 = Integer.parseInt(dialysis.get(0).getDiaId());
                                    intent.putExtra("data1", fk1);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case1", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk2 = Integer.parseInt(dialysis.get(1).getDiaId());
                                    intent.putExtra("data2", fk2);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case2", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk3 = Integer.parseInt(dialysis.get(2).getDiaId());
                                    intent.putExtra("data3", fk3);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case3", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk4 = Integer.parseInt(dialysis.get(3).getDiaId());
                                    intent.putExtra("data4", fk4);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case4", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk5 = Integer.parseInt(dialysis.get(4).getDiaId());
                                    intent.putExtra("data5", fk5);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case5", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk6 = Integer.parseInt(dialysis.get(5).getDiaId());
                                    intent.putExtra("data6", fk6);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case6", checkCase);
                                    startActivity(intent);
                                }
                            });

                            txtround7.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), RoundActivity.class);
                                    int fk7 = Integer.parseInt(dialysis.get(6).getDiaId());
                                    intent.putExtra("data7", fk7);
                                    intent.putExtra("date", finaldate);
                                    intent.putExtra("case7", checkCase);
                                    startActivity(intent);
                                }
                            });

                        } catch (Exception e) {
                            txtround1.setVisibility(View.INVISIBLE);
                            txtround2.setVisibility(View.INVISIBLE);
                            txtround3.setVisibility(View.INVISIBLE);
                            txtround4.setVisibility(View.INVISIBLE);
                            txtround5.setVisibility(View.INVISIBLE);
                            txtround6.setVisibility(View.INVISIBLE);
                            txtround7.setVisibility(View.INVISIBLE);
                            txtProfit.setVisibility(View.INVISIBLE);
                            txtUrinate.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Dialysis>> call, Throwable t) {
                        System.out.println(t.getCause() + "cause" + t.toString() + "toString" + t.getMessage());
                    }
                });
            }
        });

        initInstances(rootView, savedInstanceState);
        return rootView;

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