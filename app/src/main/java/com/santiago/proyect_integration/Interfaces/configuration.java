package com.santiago.proyect_integration.Interfaces;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class configuration {
    public static tecApiJson jsonApiRetrofit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://service-tecunity.herokuapp.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tecApiJson tecApiJson=retrofit.create(tecApiJson.class);

        return tecApiJson;
    }
}
