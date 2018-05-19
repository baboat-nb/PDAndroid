package com.project.liveat500px.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.BloodSample;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class HistoryBloodFragment extends Fragment {


    private DataBaseHelper myDb;
    private Context context;
    TextView HistoryKFloat, HistoryPFloat, HistoryAlbFloat, HistoryKText, HistoryPText, HistoryAlbText, HistoryDate,
            HistoryKDetail, HistoryPDetail, HistoryAlbDetail;
    String kfloat, pfloat, albfloat, ktext, ptext, albtext;

    //ListView listView;
    //PhotoListAdapter listAdapter;

    public HistoryBloodFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static HistoryBloodFragment newInstance() {
        HistoryBloodFragment fragment = new HistoryBloodFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_historyblood, container, false);


        HistoryDate = (TextView) rootView.findViewById(R.id.HistoryDate);
        HistoryKFloat = (TextView) rootView.findViewById(R.id.HistoryKFloat);
        HistoryPFloat = (TextView) rootView.findViewById(R.id.HistoryPFloat);
        HistoryAlbFloat = (TextView) rootView.findViewById(R.id.HistoryAlbFloat);
        HistoryKText = (TextView) rootView.findViewById(R.id.HistoryKText);
        HistoryPText = (TextView) rootView.findViewById(R.id.HistoryPText);
        HistoryAlbText = (TextView) rootView.findViewById(R.id.HistoryAlbText);
        HistoryKDetail = (TextView) rootView.findViewById(R.id.HistoryKDetail);
        HistoryPDetail = (TextView) rootView.findViewById(R.id.HistoryPDetail);
        HistoryAlbDetail = (TextView) rootView.findViewById(R.id.HistoryAlbDetail);

        String id = myDb.getAllData();
        Call<BloodSample> call = HttpManager.getInstance().getService().findBloodSample(id);
        call.enqueue(new Callback<BloodSample>() {
            @Override
            public void onResponse(Call<BloodSample> call, Response<BloodSample> response) {
                BloodSample bs = response.body();
                kfloat = bs.getbPotass() + "";
                pfloat = bs.getbPhos() + "";
                albfloat = bs.getbAlb() + "";
                ktext = bs.getGroupOfPo();
                ptext = bs.getGroupOfPhos();
                albtext = bs.getGroupOfAlb();

                String subk = ktext.substring(10, ktext.length());
                String subp = ptext.substring(8, ptext.length());
                String subalb = albtext.substring(8, albtext.length());

                Date d = bs.getDateAddBS();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String setDate = df.format(d);
                String cutDate = setDate.substring(0, 2);
                String cutMonth = setDate.substring(2, 6);
                String cutYear = setDate.substring(6, 10);
                switch (cutMonth) {
                    case "-01-":
                        cutMonth = " มกราคม ";
                        break;
                    case "-02-":
                        cutMonth = " กุมภาพันธ์ ";
                        break;
                    case "-03-":
                        cutMonth = " มีนาคม ";
                        break;
                    case "-04-":
                        cutMonth = " เมษายน ";
                        break;
                    case "-05-":
                        cutMonth = " พฤษภาคม ";
                        break;
                    case "-06-":
                        cutMonth = " มิถุนายน ";
                        break;
                    case "-07-":
                        cutMonth = " กรกฏาคม ";
                        break;
                    case "-08-":
                        cutMonth = " สิงหาคม ";
                        break;
                    case "-09-":
                        cutMonth = " กันยายน ";
                        break;
                    case "-10-":
                        cutMonth = " ตุลาคม ";
                        break;
                    case "-11-":
                        cutMonth = " พฤศจิกายน ";
                        break;
                    case "-12-":
                        cutMonth = " ธันวาคม ";
                        break;
                }
                String finalDate = cutDate + cutMonth + cutYear;
                HistoryDate.setText(finalDate);


                HistoryKFloat.setText(kfloat);
                HistoryPFloat.setText(pfloat);
                HistoryAlbFloat.setText(albfloat);
                HistoryKText.setText(subk);
                HistoryPText.setText(subp);
                HistoryAlbText.setText(subalb);

                if(subk.equals("ต่ำ")){
                    HistoryKDetail.setText("    ควรรับประทานผักและผลไม้ที่มีโพแทสเซียมสูง โดยรับประทานอาหาร ต่อ 1 วัน ที่โพแทสเซียมรวมไม่เกิน 3,000 มิลลิกรัม รับประทานจนกว่าค่าโพแทสเซียมของผลเลือดครั้งต่อไป อยู่ในเกณฑ์ปกติ");
                }else if (subk.equals("ปานกลาง")){
                    HistoryKDetail.setText("    สามารถรับประทานผักและผลไม้ใดก็ได้ โดยรับประทานอาหารต่อ 1 วัน ที่โพแทสเซียมรวมไม่เกิน 2,500 มิลลิกรัม");
                }else if (subk.equals("สูง")){
                    HistoryKDetail.setText("    ควรรับประทานผักและผลไม้ที่มีโพแทสเซียมต่ำ โดยรับประทานอาหารต่อ 1 วัน ที่โพแทสเซียมรวมไม่เกิน 2,000 มิลลิกรัม รับประทานจนกว่าค่าโพแทสเซียมของผลเลือดครั้งต่อไปอยู่ในเกณฑ์ปกติ");
                }

                if(subp.equals("ต่ำ")){
                    HistoryPDetail.setText("    ท่านอยู่ในช่วงขาดสารอาหาร สาเหตุเนื่องมากจากไม่รับประทานอาหาร ควรรับประทานอาหารต่อ 1 วัน ที่ฟอสฟอรัสไม่ต่ำกว่า 800 มิลลิกรัม และรวมอยู่ในช่วง 800-1,000 มิลลิกรัม");
                }else if (subp.equals("ปานกลาง")){
                    HistoryPDetail.setText("    ค่าฟอสฟอรัสในเลือดปกติ ควรรับประทานอาหารต่อ 1 วัน ที่ฟอสฟอรัสไม่ต่ำกว่า 800 มิลลิกรัม และรวมอยู่ในช่วง 800-1,000 มิลลิกรัม");
                }else if (subp.equals("สูง")){
                    HistoryPDetail.setText("    ควรหลีกเลี่ยงอาหารที่มีฟอสฟอรัสสูงและรับประทานอาหารต่อ 1 วัน ที่ฟอสฟอรัสรวมอยู่ในช่วง 800-1,000 มิลลิกรัม");
                }

                if(subalb.equals("ต่ำ")){
                    HistoryAlbDetail.setText("    มีหลายสาเหตุ" +
                            "- โปรตีนจากเนื้อสัตว์ไม่เพียงพอ ควรเสริมด้วยการเลือกรับประทานไข่ขาว" +
                            "- รับประทานอาหารไม่เพียงพอ ควรรับประทานอาหารให้ได้ตามมาตรฐานสารอาหารที่กำหนดไว้" +
                            "- เกิดจากร่างกายไม่สบาย");
                }else if (subalb.equals("ปกติ")){
                    HistoryAlbDetail.setText("    ค่าแอลบูมินในเลือดอยู่ในเกณฑ์ที่ปกติ");
                }else if (subalb.equals("ดี")){
                    HistoryAlbDetail.setText("    ค่าแอลบูมินในเลือดอยู่ในเกณฑ์ที่ดี");
                }
            }

            @Override
            public void onFailure(Call<BloodSample> call, Throwable t) {

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
