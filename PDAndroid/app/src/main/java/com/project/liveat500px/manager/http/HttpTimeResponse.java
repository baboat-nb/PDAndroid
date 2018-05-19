package com.project.liveat500px.manager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fame on 9/23/2017.
 */

public class HttpTimeResponse {
    private static HttpTimeResponse instance;
    private ApiService service;

    public static HttpTimeResponse getInstance() {
        if (instance == null) {
            instance = new HttpTimeResponse();

        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setDateFormat("HH:mm:ss")
            .create();

    private HttpTimeResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.229.94.27:8080/PDApp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiService.class);

    }

    public ApiService getService() {

        return service;
    }
}
