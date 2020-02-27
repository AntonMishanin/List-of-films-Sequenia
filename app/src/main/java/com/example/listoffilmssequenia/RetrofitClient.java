package com.example.listoffilmssequenia;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit retrofitInstance;

    private static Retrofit getRetrofitInctanse() {

        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://s3-eu-west-1.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static Api getApi(){
        return getRetrofitInctanse().create(Api.class);
    }
}
