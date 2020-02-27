package com.example.listoffilmssequenia;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("sequeniatesttask/films.json")
    Call<ResponseFilms> getListOfFilms();
}
