package com.project.liveat500px.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ForgetPasswordActivity extends AppCompatActivity {
    EditText email;
    Button btnSend;
    String sendEmail;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Initinstance();
    }
    private void Initinstance(){
        email = (EditText)findViewById(R.id.forgetEmail);

        btnSend = (Button)findViewById(R.id.btnSendEmail);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail = email.getText().toString();
                System.out.println("email"+sendEmail);
                Call<String> call = HttpDateResponse.getInstance().getService().sendEmail(sendEmail);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("send email response"+response.body());
                        if(response != null){
                            if(response.body().equals("true")){
                                Toast.makeText(ForgetPasswordActivity.this ,"ส่งรหัสผ่านใหม่ทาง email แล้ว" ,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                ForgetPasswordActivity.this.finish();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this ,"email ไม่ถูกต้อง" ,Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            System.out.println("error send email" + response.errorBody());

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("failure send email" + t.toString());
                        Toast.makeText(ForgetPasswordActivity.this ,"email ไม่ถูกต้อง" ,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        }
}
