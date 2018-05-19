package com.project.liveat500px.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.fragment.DataFragment;
import com.project.liveat500px.manager.http.HttpManager;
import com.project.liveat500px.manager.http.HttpTimeResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RoundActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    int data;
    String date;
    String dataId;
    TextView txtTimeInStart;
    TextView txtTimeInEnd;
    TextView txtTimeInVol;
    TextView txtTimeOutStart;
    TextView txtTimeOutEnd;
    TextView txtTimeOutVol;
    TextView txtMed;
    TextView txtdate;
    ImageView btnDelete;
    private DataFragment dataFragment1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        myDb = new DataBaseHelper(this);
        btnDelete = (ImageView) findViewById(R.id.btnDelete);
        txtdate = (TextView) findViewById(R.id.txtdate);
        txtMed = (TextView) findViewById(R.id.txtMed);
        txtTimeInStart = (TextView) findViewById(R.id.txtTimeInStart);
        txtTimeInEnd = (TextView) findViewById(R.id.txtTimeInEnd);
        txtTimeInVol = (TextView) findViewById(R.id.txtTimeInVol);
        txtTimeOutStart = (TextView) findViewById(R.id.txtTimeOutStart);
        txtTimeOutEnd = (TextView) findViewById(R.id.txtTimeOutEnd);
        txtTimeOutVol = (TextView) findViewById(R.id.txtTimeOutVol);

        Bundle extras = getIntent().getExtras();

        date = extras.getString("date");


        int data1 = extras.getInt("data1");
        int data2 = extras.getInt("data2");
        int data3 = extras.getInt("data3");
        int data4 = extras.getInt("data4");
        int data5 = extras.getInt("data5");
        int data6 = extras.getInt("data6");
        int data7 = extras.getInt("data7");


        if (data1 != 0) {
            data = data1;
        }
        if (data2 != 0) {
            data = data2;
        }
        if (data3 != 0) {
            data = data3;
        }
        if (data4 != 0) {
            data = data4;
        }
        if (data5 != 0) {
            data = data5;
        }
        if (data6 != 0) {
            data = data6;
        }
        if (data7 != 0) {
            data = data7;
        }

        dataId = data + "";
        System.out.println(dataId);

        int case1 = extras.getInt("case1");
        int case2 = extras.getInt("case2");
        int case3 = extras.getInt("case3");
        int case4 = extras.getInt("case4");
        int case5 = extras.getInt("case5");
        int case6 = extras.getInt("case6");
        int case7 = extras.getInt("case7");
        System.out.println(case1);
        System.out.println(case2);
        System.out.println(case3);
        System.out.println(case4);
        System.out.println(case5);
        System.out.println(case6);
        System.out.println(case7);

        initInstances();

    }


    private void initInstances() {

        Call call = HttpTimeResponse.getInstance().getService().find(dataId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("CALL");
                final Dialysis dialysis = (Dialysis) response.body();
                Date instartD = dialysis.getTimeDiaIn_start();
                Date inendD = dialysis.getTimeDiaIn_end();
                Date outstartD = dialysis.getTimeDiaOut_start();
                Date outendD = dialysis.getTimeDiaOut_end();
                DateFormat df = new SimpleDateFormat("HH:mm");
                String instart = df.format(instartD);
                String inend = df.format(inendD);
                String outstart = df.format(outstartD);
                String outend = df.format(outendD);

                txtTimeInStart.setText(instart);
                txtTimeInEnd.setText(inend);
                txtTimeOutStart.setText(outstart);
                txtTimeOutEnd.setText(outend);

                String invol = dialysis.getVolDiaIn() + "";
                String outvol = dialysis.getVolDiaOut() + "";
                txtTimeInVol.setText(invol);
                txtTimeOutVol.setText(outvol);
                txtMed.setText(dialysis.getDesDiaLiquid());
                txtdate.setText(date);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call call = HttpManager.getInstance().getService().deleteDialysis(dataId);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                Call calls = HttpManager.getInstance().getService().deleteRecord(dialysis.getRecord_recId_fk() + "");
                                calls.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        Toast.makeText(RoundActivity.this, "ลบข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("edit", "change");
                                        editor.apply();

                                        RoundActivity.this.finish();
//                                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, new DataFragment()).commit();
//                                        Fragment fragment = new DataFragment();
//                                        FragmentManager manager = getSupportFragmentManager();
//                                        FragmentTransaction transaction = manager.beginTransaction();
//                                        transaction.replace(R.id.contentContainer,fragment);
//                                        transaction.commit();
//                                        Intent intent = new (RoundActivity.this,DataFragment.class);
//                                        intent
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getCause() + " " + t.toString());
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}