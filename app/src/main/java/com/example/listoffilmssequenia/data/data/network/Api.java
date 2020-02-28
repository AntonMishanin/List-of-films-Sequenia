package com.example.listoffilmssequenia.data.data.network;

import com.example.listoffilmssequenia.data.data.model.ResponseFilms;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("sequeniatesttask/films.json")
    Call<ResponseFilms> getListOfFilms();
}
