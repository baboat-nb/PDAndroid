package com.project.liveat500px.manager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fame on 9/19/2017.
 */

public class HttpDateResponse {

    private static HttpDateResponse instance;
    private ApiService service;

    public static HttpDateResponse getInstance() {
        if (instance == null) {
            instance = new HttpDateResponse();

        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    private HttpDateResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.229.94.27:8080/PDApp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiService.class);

/*
        service.loadPhotoList().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                object = response.body();
                MainActivity.tvHello.setText(object.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
*/
    }

    public ApiService getService() {

        return service;
    }
}
