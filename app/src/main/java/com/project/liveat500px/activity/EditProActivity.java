package com.project.liveat500px.activity;

import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.liveat500px.Dao.Doctor;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Jenny on 9/11/17.
 */

public class EditProActivity extends AppCompatActivity {
    private DataBaseHelper myDb;
    private EditText name;
    private EditText lastName;
    private EditText sex;
    private TextView birthDay;
    private TextView age;
    private EditText weight;
    private EditText height;
    private EditText bloodPressure;
    private MaterialSpinner hospital;
    private EditText tel;
    private EditText telDia;
    private EditText telHos;
    private TextView editEmail;
    private MaterialSpinner editDoctor;
    private Button editBtn;
    TextView DOCID;
    private String id;
    int year, month, day;
    SimpleDateFormat parseDate;
    CircleImageView editprofile_image;
    public static final int GET_FROM_GALLERY = 3;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpro);
        myDb = new DataBaseHelper(this);


        showProfile();
        updateProfile();

    }

    private void showProfile() {

        id = myDb.getAllData();
        parseDate = new SimpleDateFormat("yyyy-MM-dd");
        Call<Patient> call = HttpManager.getInstance().getService().findPatient(id);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response != null) {
                    Patient p = response.body();

                    name.setText(p.getPatFirstname());
                    lastName.setText(p.getPatLastname());
                    sex.setText(p.getPatSex());
                    age.setText(String.valueOf(p.getPatAge()));
                    weight.setText(String.valueOf(p.getWeight()));
                    height.setText(String.valueOf(p.getPatHeight()));
                    bloodPressure.setText(p.getBloodPressure());
                    final String hospitalName = p.getHospName();
                    Call<List<String>> findHospname = HttpManager.getInstance().getService().findHospital();
                    findHospname.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            List<String> listHos = response.body();
                            hospital.setItems(listHos);
                            for(int i =0;i<listHos.size();i++){
                                if(listHos.get(i).equals(hospitalName)){
                                    hospital.setSelectedIndex(i);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });

//                    hospital.setText(p.getHospName());
                    tel.setText(p.getTel());
                    telDia.setText(p.getTelCenter());
                    telHos.setText(p.getTelNurse());
//                    editDoctor.setText(p.getDoctor_docId_fk());
                    DOCID.setText(p.getDoctor_docId_fk());
                    editEmail.setText(p.getEmail());
                    birthDay.setText(parseDate.format(p.getPatBirthday().getTime()));
                    String doctorId = p.getDoctor_docId_fk();

                    Call<Doctor> callDoc = HttpManager.getInstance().getService().findHospname(doctorId);
                    callDoc.enqueue(new Callback<Doctor>() {
                        @Override
                        public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                            Doctor d = response.body();
                            editDoctor.setText(d.getDocFirstname()+ " " + d.getDocLastname());
                        }

                        @Override
                        public void onFailure(Call<Doctor> call, Throwable t) {

                        }
                    });

//                    Call<List<Doctor>> call2 = HttpManager.getInstance().getService().findByDocHospName(p);
//                    call2.enqueue(new Callback<List<Doctor>>() {
//                        @Override
//                        public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
//                            List<Doctor> d = response.body();
//                            for(int i=0;i<d.size();i++){
//                                editDoctor.setItems(d.get(i).getDocFirstname());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Doctor>> call, Throwable t) {
//
//                        }
//                    });

                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }

    private void updateProfile() {

        name = (EditText) findViewById(R.id.editName);
        lastName = (EditText) findViewById(R.id.editLastName);
        sex = (EditText) findViewById(R.id.editSex);
        birthDay = (TextView) findViewById(R.id.editBirth);
        age = (TextView) findViewById(R.id.editAge);
        weight = (EditText) findViewById(R.id.editWeight);
        height = (EditText) findViewById(R.id.editHeight);
        bloodPressure = (EditText) findViewById(R.id.editBloodPressure);
        hospital = (MaterialSpinner) findViewById(R.id.editHospital);
        tel = (EditText) findViewById(R.id.editTel);
        telDia = (EditText) findViewById(R.id.editTelDia);
        telHos = (EditText) findViewById(R.id.editTelHospital);
        editEmail = (TextView) findViewById(R.id.editEmail);
        editDoctor = (MaterialSpinner) findViewById(R.id.editDoctor);
        DOCID = (TextView) findViewById(R.id.DOCID);
        editBtn = (Button) findViewById(R.id.editBtn);
        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) EditProActivity.this
                        .getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name, 0);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseDate = new SimpleDateFormat("yyyy-MM-dd");
                id = myDb.getAllData();
                try {
                    Patient patient = new Patient();
                    patient.setPatId(id);
                    patient.setPatFirstname(name.getText().toString());
                    patient.setPatLastname(lastName.getText().toString());
                    patient.setPatSex(sex.getText().toString());
                    patient.setPatBirthday(parseDate.parse(birthDay.getText().toString()));
                    patient.setPatAge(Integer.parseInt(age.getText().toString()));
                    patient.setWeight(Integer.parseInt(weight.getText().toString()));
                    patient.setPatHeight(Integer.parseInt(height.getText().toString()));
                    patient.setBloodPressure(bloodPressure.getText().toString());
                    patient.setHospName(hospital.getText().toString());
                    patient.setTel(tel.getText().toString());
                    patient.setTelCenter(telDia.getText().toString());
                    patient.setTelNurse(telHos.getText().toString());
                    patient.setDoctor_docId_fk(DOCID.getText().toString());
                    patient.setEmail(editEmail.getText().toString());



                    Call<Patient> call = HttpDateResponse.getInstance().getService().updateProfile(patient);
                    call.enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response != null) {
                                System.out.println("patient" + response.body());
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EditProActivity.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("name", name.getText().toString());
                                editor.putString("lastName", lastName.getText().toString());
                                editor.putString("telDia", telDia.getText().toString());
                                editor.putString("telHos", telHos.getText().toString());
                                editor.commit();

                                Intent intent = new Intent(EditProActivity.this, MainActivity.class);
                                startActivity(intent);
                                EditProActivity.this.finish();
                            } else {
                                System.out.println("error" + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            System.out.println("error" + t.toString());
                            Toast.makeText(EditProActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

            }
        });

        editprofile_image = (CircleImageView) findViewById(R.id.editprofile_image);
        editprofile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    private void createDatePickerDialog() {
        id = myDb.getAllData();
        parseDate = new SimpleDateFormat("yyyy-MM-dd");
        Call<Patient> call = HttpManager.getInstance().getService().findPatient(id);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

                Patient pateint = response.body();
                String date = parseDate.format(pateint.getPatBirthday());
                String arr[] = date.split("-");
                day = Integer.parseInt(arr[2]);
                month = Integer.parseInt(arr[1]);
                year = Integer.parseInt(arr[0]);
                System.out.println("dayyy" + date);
                System.out.println("day" + day + "/month" + month + "/year" + year);

                DatePickerDialog dialog = new DatePickerDialog(EditProActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                setDatePicker(year, month, dayOfMonth);
                            }
                        }, year, month, day);
                dialog.show();

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

    }

    private void setDatePicker(int year, int month, int day) {
        birthDay = (TextView) findViewById(R.id.editBirth);
        String date = year + "-" + month + "-" + day;
        System.out.println(date);
        birthDay.setText(date);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == MainActivity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}