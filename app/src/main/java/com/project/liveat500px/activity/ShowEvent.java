package com.project.liveat500px.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.project.liveat500px.R;
import com.project.liveat500px.fragment.AlarmFragment;
import com.project.liveat500px.fragment.SetAlarmFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowEvent extends AppCompatActivity {

    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    Ringtone r;
    Button btnStop;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        km=(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        kl=km.newKeyguardLock("ShowEvent");
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.ON_AFTER_RELEASE, "ShowEvent");
        wl.acquire(); //wake up the screen
        kl.disableKeyguard();

        setContentView(R.layout.activity_show_event);
        System.out.println("show event start");
        btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnStop){
                    //AlarmFragment.listValue.remove(0);
                    Intent intent = new Intent(ShowEvent.this, MainActivity.class);
                    startActivity(intent);
                    ShowEvent.this.finish();
                }
            }
        });

    }


    @Override
    protected void onResume() {

        super.onResume();
        wl.acquire();//must call this!
        Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if(notif==null){
//            notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            if(notif==null){
//                notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            }
//        }
        r = RingtoneManager.getRingtone(getApplicationContext(), notif);
        r.play();


    }

    @Override
    public void onPause(){
        super.onPause();
        wl.release();
        if(r.isPlaying()){
            r.stop();
        }
    }
}