package com.project.liveat500px.Service;

import android.content.Context;
import android.content.Intent;

import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fame on 11/5/2017.
 */

public class NotiGraphReceive extends ReceiveBoardCast {

    DataBaseHelper myDb;
    @Override
    public void onReceive(Context context, Intent intent) {

        myDb = new DataBaseHelper(context);
        System.out.println("reciever noti graph");
        setStatusNoti();
        Intent background = new Intent(context, NotiGraphService.class);
        context.startService(background);
    }

    private void setStatusNoti(){ //set status = 0
       String id = myDb.getAllData();
        Call<Void> call = HttpManager.getInstance().getService().setNotiGraphZero(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("set noti graph zero");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
