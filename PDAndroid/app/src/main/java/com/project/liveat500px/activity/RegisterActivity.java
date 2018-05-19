package com.project.liveat500px.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    private EditText regisUsername;
    private EditText regisPassword;
    private EditText regisFirstname;
    private EditText regisLastname;
    private EditText regisBirthday;
    private EditText regisWeight;
    private EditText regisHeight,regisEmail;
    private RadioGroup radioGroupRegis;
    private RadioButton regisMale;
    private RadioButton regisFemale;
    private SimpleDateFormat parseDate;
    private int year, month, day;
    private Patient patient;
    private String id ;
    private MaterialSpinner regisHospname;
    private Button btnRegister;
    int position =0;
    ArrayList<String> userN = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mydb = new DataBaseHelper(this);
        registerPatient();
    }
    private void registerPatient(){
        parseDate = new SimpleDateFormat("yyyy-MM-dd");
        regisUsername = (EditText) findViewById(R.id.regisUsername);
        regisPassword = (EditText) findViewById(R.id.regisPassword);
        regisFirstname = (EditText) findViewById(R.id.regisFirstname);
        regisLastname = (EditText) findViewById(R.id.regisLastname);
        regisBirthday = (EditText) findViewById(R.id.regisBirth);
        regisHeight = (EditText) findViewById(R.id.regisHeight);
        regisWeight = (EditText) findViewById(R.id.regisWeight);
        radioGroupRegis = (RadioGroup) findViewById(R.id.radioGroupRegis);
        regisMale = (RadioButton) findViewById(R.id.regisMale);
        regisFemale = (RadioButton) findViewById(R.id.regisFemale);
        regisHospname = (MaterialSpinner) findViewById(R.id.regisHospname);
        btnRegister = (Button) findViewById(R.id.button_register);
        regisEmail = (EditText) findViewById(R.id.regisEmail);
        Call<List<String>> listHosName = HttpManager.getInstance().getService().findHospName();
        listHosName.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response !=null){
                    List<String> listHos = response.body();
                    regisHospname.setItems(listHos);
                }else{
                    System.out.println("error find hosptal"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("failure find hosptal"+ t.toString());
            }
        });

        patient = new Patient();

        switch (radioGroupRegis.getCheckedRadioButtonId()){
            case R.id.regisMale :
                patient.setPatSex(regisMale.getText().toString());
                break;
            case R.id.regisFemale:
                patient.setPatSex(regisFemale.getText().toString());
                break;
            default:
                patient.setPatSex(regisMale.getText().toString());

        }
        regisBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog();
            }
        });

        regisHospname.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view,int position, long id, Object item) {
//                patient.setHospName(item.toString());
                patient.setHospName(regisHospname.getItems().get(position).toString());
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    patient.setPatUsername(regisUsername.getText().toString());
                    patient.setPatPassword(regisPassword.getText().toString());
                    patient.setPatFirstname(regisFirstname.getText().toString());
                    patient.setPatLastname(regisLastname.getText().toString());
                    patient.setPatHeight(Integer.parseInt(regisHeight.getText().toString()));
                    patient.setWeight(Integer.parseInt(regisWeight.getText().toString()));
                    patient.setEmail(regisEmail.getText().toString());
//                    patient.setHospName(regisHospname.getItems().get(0).toString());
                    try {
                        patient.setPatBirthday(parseDate.parse(regisBirthday.getText().toString()));
                    } catch (ParseException e) {
                        System.out.println("parse date register error" + e.toString());
                    }
                    Call<Integer> saveRegis = HttpDateResponse.getInstance().getService().register(patient);
                    saveRegis.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response != null) {
                                if (response.body() == 1) {
                                    Toast.makeText(RegisterActivity.this, "สร้างบัญชีแล้ว", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    RegisterActivity.this.finish();
                                } else if (response.body() == 2) {
                                    Toast.makeText(RegisterActivity.this, "มีชื่อผู้ใช้บัญชีนี้แล้ว", Toast.LENGTH_SHORT).show();
                                    System.out.println(regisEmail.getText().toString()+"EMAIL");
                                } else if (response.body() == 3) {
                                    Toast.makeText(RegisterActivity.this, "อีเมลนี้ถูกใช้แล้ว", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                System.out.println("error register " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            System.out.println("failure register " + t.toString());
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "กรุณากรอกค่าให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void createDatePickerDialog() {
        id = mydb.getAllData();
        parseDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setDatePicker(year, month, dayOfMonth);
                    }
                }, year, month, day);
        dialog.show();
    }

    private void setDatePicker(int year, int month, int day) {
        regisBirthday = (EditText) findViewById(R.id.regisBirth);
        String date = year + "-" + month + "-" + day;
        System.out.println(date);
        regisBirthday.setText(date);
    }

//    public boolean findNum() {
//
//        fxUnit = new ArrayList<String>();
//        for (int i = 0; i < r.size(); i++) {
//            String xUnit = String.valueOf(r.get(i).getUnit());
//            fxUnit.add(xUnit);
//        }
//
//        boolean found = false;
//        for (int i : arraynumbers) {
//            if (num == i) {
//                found = true;
//            }
//        }
//
//        return found;
//    }
}
