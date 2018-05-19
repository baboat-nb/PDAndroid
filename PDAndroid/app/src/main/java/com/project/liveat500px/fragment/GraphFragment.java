package com.project.liveat500px.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.MyValueFormatter;
import com.project.liveat500px.MyValueFormatter2;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class GraphFragment extends Fragment {

    //ListView listView;
    //PhotoListAdapter listAdapter;
    LineChart GraphProfitChart;
    LineChart GraphUrinateChart;
    MaterialSpinner GraphspinnerMonth;
    MaterialSpinner GraphspinnerYear;
    String GraphMonth;
    String GraphYear;
    FancyButton GraphbtnSearch;
    DataBaseHelper myDb;
    //    ArrayList<Integer> dateList = null;
//    ArrayList<Integer> totalList = null;
//    ArrayList<Integer> profit2 = null;
    SimpleDateFormat parseDate;


    public GraphFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static GraphFragment newInstance() {
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DataBaseHelper(getActivity());
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        initInstances(rootView, savedInstanceState);
        GraphbtnSearch = (FancyButton) rootView.findViewById(R.id.GraphbtnSearch);
        GraphspinnerMonth = (MaterialSpinner) rootView.findViewById(R.id.GraphspinnerMonth);
        GraphspinnerMonth.setItems("มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฏาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม");
        GraphspinnerMonth.setDropdownHeight(300);
        Date dd = new Date();
        String thisMonth = dd.toString().substring(4, 7);
        switch (thisMonth) {
            case "Jan":
                GraphspinnerMonth.setSelectedIndex(0);
                break;
            case "Feb":
                GraphspinnerMonth.setSelectedIndex(1);
                break;
            case "Mar":
                GraphspinnerMonth.setSelectedIndex(2);
                break;
            case "Apr":
                GraphspinnerMonth.setSelectedIndex(3);
                break;
            case "May":
                GraphspinnerMonth.setSelectedIndex(4);
                break;
            case "Jun":
                GraphspinnerMonth.setSelectedIndex(5);
                break;
            case "Jul":
                GraphspinnerMonth.setSelectedIndex(6);
                break;
            case "Aug":
                GraphspinnerMonth.setSelectedIndex(7);
                break;
            case "Sep":
                GraphspinnerMonth.setSelectedIndex(8);
                break;
            case "Oct":
                GraphspinnerMonth.setSelectedIndex(9);
                break;
            case "Nov":
                GraphspinnerMonth.setSelectedIndex(10);
                break;
            case "Dec":
                GraphspinnerMonth.setSelectedIndex(11);
                break;
        }
        GraphMonth = GraphspinnerMonth.getItems().get(0).toString();
        switch (GraphMonth) {
            case "มกราคม":
                GraphMonth = "01";
                break;
            case "กุมภาพันธ์":
                GraphMonth = "02";
                break;
            case "มีนาคม":
                GraphMonth = "03";
                break;
            case "เมษายน":
                GraphMonth = "04";
                break;
            case "พฤษภาคม":
                GraphMonth = "05";
                break;
            case "มิถุนายน":
                GraphMonth = "06";
                break;
            case "กรกฏาคม":
                GraphMonth = "07";
                break;
            case "สิงหาคม":
                GraphMonth = "08";
                break;
            case "กันยายน":
                GraphMonth = "09";
                break;
            case "ตุลาคม":
                GraphMonth = "10";
                break;
            case "พฤศจิกายน":
                GraphMonth = "11";
                break;
            case "ธันวาคม":
                GraphMonth = "12";
                break;
        }

        GraphspinnerYear = (MaterialSpinner) rootView.findViewById(R.id.GraphspinnerYear);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2000; i--) {
            years.add(Integer.toString(i));
        }
        GraphspinnerYear.setItems(years);
        GraphspinnerYear.setDropdownHeight(300);
        GraphYear = GraphspinnerYear.getItems().get(0).toString();

        System.out.println("BBBMB " + GraphMonth);
        System.out.println("BBBYB " + GraphYear);

        GraphspinnerMonth.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                GraphMonth = item.toString();

                switch (GraphMonth) {
                    case "มกราคม":
                        GraphMonth = "01";
                        break;
                    case "กุมภาพันธ์":
                        GraphMonth = "02";
                        break;
                    case "มีนาคม":
                        GraphMonth = "03";
                        break;
                    case "เมษายน":
                        GraphMonth = "04";
                        break;
                    case "พฤษภาคม":
                        GraphMonth = "05";
                        break;
                    case "มิถุนายน":
                        GraphMonth = "06";
                        break;
                    case "กรกฏาคม":
                        GraphMonth = "07";
                        break;
                    case "สิงหาคม":
                        GraphMonth = "08";
                        break;
                    case "กันยายน":
                        GraphMonth = "09";
                        break;
                    case "ตุลาคม":
                        GraphMonth = "10";
                        break;
                    case "พฤศจิกายน":
                        GraphMonth = "11";
                        break;
                    case "ธันวาคม":
                        GraphMonth = "12";
                        break;
                }
            }
        });

        GraphspinnerYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                GraphYear = item.toString();
            }
        });

        GraphbtnSearch.setCustomTextFont("fonts/Kanit-Regular.ttf");
        GraphbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myDb.getAllData();
                final int month = Integer.parseInt(GraphMonth);
                parseDate = new SimpleDateFormat("dd-MM-yyyy");
                Call<List<Record>> call = HttpManager.getInstance().getService().findRecordByByte(id, month);
                call.enqueue(new Callback<List<Record>>() {
                    @Override
                    public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                        List<Record> record = response.body();
                        ArrayList<Integer> dateList = new ArrayList<Integer>();
                        ArrayList<Integer> totalList = new ArrayList<Integer>();
                        ArrayList<Integer> profit2 = new ArrayList<Integer>();
                        for (int i = 0; i < record.size(); i++) {
                            String date = parseDate.format(record.get(i).getRecDate());
                            String month = date.substring(3, 5);
                            date = date.substring(0, 2);
                            int NumDate = Integer.parseInt(date);
                            int Total = record.get(i).getTotalProfit();
                            int profit3 = record.get(i).getTotalUrinate();
                            dateList.add(NumDate);
                            totalList.add(Total);
                            profit2.add(profit3);
                        }

                        int Red = Color.RED;
                        int Blue = Color.BLUE;

                        if (dateList.size() == 0) {
                            Toast.makeText(getActivity(), "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                            System.out.println(GraphProfitChart+" GRGRGR");
                            if (GraphProfitChart.getLineData() != null) {
                                GraphProfitChart.clearValues();
                            }
                            if (GraphUrinateChart.getLineData() != null) {
                                GraphUrinateChart.clearValues();
                            }
                        } else if (dateList.size() != 0) {
                            List<Entry> entries = new ArrayList<Entry>();
                            for (int j = 0; j < dateList.size(); j++) {

                                // turn your data into Entry objects
                                try {
                                    entries.add(new Entry(dateList.get(j), totalList.get(j)));

                                } catch (IndexOutOfBoundsException e) {
                                    Toast.makeText(getActivity(), "Out", Toast.LENGTH_SHORT).show();
                                }
                            }

                            LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                            dataSet.setColor(Red);
                            dataSet.setDrawCircles(true);
                            dataSet.setCircleColor(Red);
                            dataSet.setValueTextColor(Red);
                            dataSet.setDrawCircleHole(false);
                            dataSet.setLabel("ค่าสุทธิการล้างไต");
                            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            dataSet.setCubicIntensity(0.01f);
                            dataSet.setCircleRadius(2);
                            dataSet.setValueTextSize(9f);
                            dataSet.setValueFormatter(new MyValueFormatter());

                            LineData lineData = new LineData(dataSet);

                            GraphProfitChart.setData(lineData);
                            GraphProfitChart.invalidate();
                            GraphProfitChart.setDrawBorders(false);
                            GraphProfitChart.setTouchEnabled(false);
                            GraphProfitChart.setHorizontalScrollBarEnabled(true);
                            XAxis topAxis = GraphProfitChart.getXAxis();
                            topAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            topAxis.setLabelCount(31);
                            topAxis.setAxisMinimum(1);
                            topAxis.setAxisMaximum(31);
                            topAxis.setGridColor(Color.TRANSPARENT);
                            topAxis.setValueFormatter(new MyValueFormatter2());

                            YAxis rightAxis = GraphProfitChart.getAxisRight();
                            rightAxis.setEnabled(false);

                            YAxis leftAxis = GraphProfitChart.getAxisLeft();
                            leftAxis.setLabelCount(6, true);
                            leftAxis.setDrawZeroLine(true);
                            leftAxis.setAxisMinimum(-3000);
                            leftAxis.setAxisMaximum(3000);


                            List<Entry> entries2 = new ArrayList<Entry>();

                            for (int k = 0; k < dateList.size(); k++) {
                                // turn your data into Entry objects
                                try {
                                    entries2.add(new Entry(dateList.get(k), profit2.get(k)));

                                } catch (IndexOutOfBoundsException e) {
                                    Toast.makeText(getActivity(), "Out", Toast.LENGTH_SHORT).show();
                                }
                            }

                            System.out.println(entries2 + "BOAT");
                            LineDataSet dataSet2 = new LineDataSet(entries2, "Label"); // add entries to dataset
                            dataSet2.setColor(Color.GREEN);
                            dataSet2.setValueTextColor(Color.GREEN);
                            dataSet2.setCircleColor(Color.GREEN);
                            dataSet2.setDrawCircles(true);
                            dataSet2.setDrawCircleHole(false);
                            dataSet2.setLabel("ค่าสุทธิปัสสาวะ");
                            dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            dataSet2.setCubicIntensity(0.01f);
                            dataSet2.setCircleRadius(2);
                            dataSet2.setValueTextSize(9f);
                            dataSet2.setValueFormatter(new MyValueFormatter());
                            LineData lineData2 = new LineData(dataSet2);

                            GraphUrinateChart.setData(lineData2);
                            GraphUrinateChart.invalidate();
                            GraphUrinateChart.setDrawBorders(false);
                            GraphUrinateChart.setTouchEnabled(false);
                            GraphUrinateChart.setHorizontalScrollBarEnabled(true);

                            XAxis topAxis2 = GraphUrinateChart.getXAxis();
                            topAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                            topAxis2.setLabelCount(31);
                            topAxis2.setAxisMinimum(1);
                            topAxis2.setAxisMaximum(31);
                            topAxis2.setGridColor(Color.TRANSPARENT);
                            topAxis2.setValueFormatter(new MyValueFormatter2());

                            YAxis rightAxis2 = GraphUrinateChart.getAxisRight();
                            rightAxis2.setEnabled(false);

                            YAxis leftAxis2 = GraphUrinateChart.getAxisLeft();
                            leftAxis2.setLabelCount(6, true);
                            leftAxis2.setAxisMinimum(0);
                            leftAxis2.setAxisMaximum(1000);

                            dateList.removeAll(dateList);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Record>> call, Throwable t) {

                    }
                });
            }
        });


        GraphProfitChart = (LineChart) rootView.findViewById(R.id.GraphProfitChart);
        GraphUrinateChart = (LineChart) rootView.findViewById(R.id.GraphUrinateChart);


//        YourData[] dataObjects = ;


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
