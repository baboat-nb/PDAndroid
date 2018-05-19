package com.project.liveat500px.activity;

import android.app.DatePickerDialog;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Jenny on 9/11/17.
 */

public class EditPassActivity extends AppCompatActivity {
    private DataBaseHelper myDb;
    EditText EditOldPassword,EditNewPassword,EditConfirmPassword;
Button EditPassword_button;
String id;
int patPass;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
        myDb = new DataBaseHelper(this);

        id = myDb.getAllData();
        EditOldPassword = (EditText) findViewById(R.id.EditOldPassword);
        EditNewPassword = (EditText) findViewById(R.id.EditNewPassword);
        EditConfirmPassword = (EditText) findViewById(R.id.EditConfirmPassword);

        Call <Patient> callP = HttpManager.getInstance().getService().findPatient(id);
        callP.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                Patient patient = response.body();
                patPass = Integer.parseInt(patient.getPatPassword());
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });

        EditPassword_button = (Button) findViewById(R.id.EditPassword_button);
        EditPassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String oldPass = EditOldPassword.getText().toString();
                final String newPass = EditNewPassword.getText().toString();
                final String confirmPass = EditConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(newPass) || newPass.length() < 8)
                {
                    EditNewPassword.requestFocus();
                    EditNewPassword.setError("รหัสผ่านต้องมีอย่างน้อย 8 ตัว");
                    return;
                }

                int compare = oldPass.hashCode();
                if(compare == patPass ) {


                    if (newPass.equals(confirmPass)) {
                        Call<Boolean> call = HttpManager.getInstance().getService().changePassword(id, oldPass, newPass);
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                EditPassActivity.this.finish();
                                Toast.makeText(EditPassActivity.this,"เปลี่ยนรหัสผ่านสำเร็จ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });

                    } else {
                        EditConfirmPassword.requestFocus();
                        EditConfirmPassword.setError("รหัสผ่านใหม่ไม่ตรงกัน");
                    }
                }else if (compare != patPass){
                    EditOldPassword.requestFocus();
                    EditOldPassword.setError("รหัสผ่านเก่าไม่ตรงกัน");
                }
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