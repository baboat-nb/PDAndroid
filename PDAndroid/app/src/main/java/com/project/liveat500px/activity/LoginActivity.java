package com.project.liveat500px.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.manager.UserManager;
import com.project.liveat500px.manager.http.HttpManager;

import org.json.JSONObject;

import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by baboat on 13/9/2560.
 */

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    Context mContext;
    private UserManager mManager;
    private DataBaseHelper myDb;
    private TextView forgetPass;
    ImageView LoginBack;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initInstances();
        myDb = new DataBaseHelper(this);
        LoginBack = (ImageView) findViewById(R.id.LoginBack);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        txtRegister = (TextView) findViewById(R.id.txt_register);
        txtRegister.setPaintFlags(txtRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mLogin = (Button) findViewById(R.id.button_login);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        System.out.println("myDb value :" + myDb.getAllData());
        if (!myDb.getAllData().isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call call = HttpManager.getInstance().getService().login(mUsername.getText().toString(), mPassword.getText().toString());
                System.out.println(call);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            if (response != null) {

                                Patient patient = (Patient) response.body();
                                int newPass = mPassword.getText().toString().hashCode();
                                if (patient.getPatUsername().equalsIgnoreCase(mUsername.getText().toString()) && patient.getPatPassword().equals(String.valueOf(newPass))) {

                                    String patId = patient.getPatId();

                                    boolean result = myDb.insertData(patId);
                                    System.out.println("Login result : " + result);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                } else {
                                    String message = "Username or Password is incorrect.";
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "else", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        System.out.println(t.toString());
                        Log.e("d", t.toString());
                    }
                });
            }
        });

        forgetPass();
//        register();
    }

    private void forgetPass() {
        forgetPass = (TextView) findViewById(R.id.forgetPassLoginPage);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    //    private void register(){
//        btnRegister = (Button) findViewById(R.id.button_register);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
//            }
//        });
//    }
//    private void initInstances() {
//
//        btnLogin = (Button) findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences settings = getSharedPreferences("your_preference_name", 0);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("LoggedIn", true);
//                editor.commit();
//
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                LoginActivity.this.finish();
//
//                settings = getSharedPreferences("your_preference_name", 0);
//                boolean isLoggedIn = settings.getBoolean("LoggedIn", false);
//
//                if(isLoggedIn )
//                {
//                    //Go directly to Homescreen.
//                }
//            }
//        });
//    }
    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


//    private void checkLogin() {
//        String username = mUsername.getText().toString().trim().toLowerCase();
//        String password = mPassword.getText().toString().trim();
//
//        boolean isSuccess = true;
//        //boolean isSuccess = mManager.checkLoginValidate(username, password);
//
//        if (isSuccess) {
//            Intent intent = new Intent(mContext, MainActivity.class);
//            startActivity(intent);
//
//        } else {
//            String message = "Username or Password is incorrect.";
//            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//        }
//    }
}