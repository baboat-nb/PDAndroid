package com.project.liveat500px.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.fragment.ChooseBloodFragment;
import com.project.liveat500px.fragment.HistoryBloodFragment;
import com.project.liveat500px.fragment.InsertBloodFragment;
import com.project.liveat500px.fragment.MainFragment;
import com.project.liveat500px.manager.UserManager;
import com.project.liveat500px.manager.http.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by baboat on 13/9/2560.
 */

public class ChooseBloodActivity extends AppCompatActivity {

    TextView TabChooseBlood;
    TextView TabInsertBlood;
    TextView TabHistoryBlood;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseblood);



        initInstances();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentBlood, HistoryBloodFragment.newInstance())
                    .commit();
        }

        TabHistoryBlood = (TextView) findViewById(R.id.TabHistoryBlood);
        TabHistoryBlood.setBackgroundColor(Color.WHITE);
        TabHistoryBlood.setTextColor(getResources().getColor(R.color.colorPrimary));
        TabHistoryBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabHistoryBlood.setBackgroundColor(Color.WHITE);
                TabHistoryBlood.setTextColor(0xFF8C0B00);
                TabChooseBlood.setBackgroundColor(0xFFE02616);
                TabChooseBlood.setTextColor(Color.WHITE);
                TabInsertBlood.setBackgroundColor(0xFFE02616);
                TabInsertBlood.setTextColor(Color.WHITE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentBlood, new HistoryBloodFragment()).commit();
            }
        });

        TabChooseBlood = (TextView) findViewById(R.id.TabChooseBlood);
        TabChooseBlood.setBackgroundColor(0xFFE02616);
        TabChooseBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabChooseBlood.setBackgroundColor(Color.WHITE);
                TabInsertBlood.setBackgroundColor(0xFFE02616);
                TabInsertBlood.setTextColor(Color.WHITE);
                TabHistoryBlood.setTextColor(Color.WHITE);
                TabHistoryBlood.setBackgroundColor(0xFFE02616);
                TabChooseBlood.setTextColor(0xFF8C0B00);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentBlood, new ChooseBloodFragment()).commit();
            }
        });


        TabInsertBlood = (TextView) findViewById(R.id.TabInsertBlood);
        TabInsertBlood.setBackgroundColor(0xFFE02616);
        TabInsertBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabInsertBlood.setBackgroundColor(Color.WHITE);
                TabChooseBlood.setBackgroundColor(0xFFE02616);
                TabChooseBlood.setTextColor(Color.WHITE);
                TabHistoryBlood.setBackgroundColor(0xFFE02616);
                TabHistoryBlood.setTextColor(Color.WHITE);
                TabInsertBlood.setTextColor(0xFF8C0B00);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentBlood, new InsertBloodFragment()).commit();
            }
        });
    }


    private void initInstances() {

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