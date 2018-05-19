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
 * Created by baboat on 13/9/2560.
 */

public class HttpManager {

    private static HttpManager instance;
    private ApiService service;

    public static HttpManager getInstance(){
        if(instance == null){
            instance = new HttpManager();

        }
        return instance;
    }

    private HttpManager(){

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .create();

        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.179.129.213:8080/PDApp/")
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

    public ApiService getService(){

        return  service;
    }

}
