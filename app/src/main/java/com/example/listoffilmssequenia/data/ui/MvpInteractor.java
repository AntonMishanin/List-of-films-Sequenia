package com.example.listoffilmssequenia.data.ui;

import androidx.annotation.NonNull;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.model.ResponseFilms;
import com.example.listoffilmssequenia.data.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MvpInteractor implements Interactor {

    private List<Film> films = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private List<String> genres2 = new ArrayList<>();


    @Override
    public void getListOfFilms(final OnListOfFilmsListener onListOfFilmsListener) {
        RetrofitClient.getApi().getListOfFilms().enqueue(new Callback<ResponseFilms>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFilms> call, @NonNull Response<ResponseFilms> response) {
                ResponseFilms responseFilms = response.body();
                films.addAll(responseFilms.getFilms());
                onListOfFilmsListener.setListOfFilms(films);

                for (int i = 0; i < films.size(); i++) {
                    genres.addAll(films.get(i).getGenres());
                }

                for (int i = 0; i < genres.size(); i++) {
                    int countUniqueGenresInArray = 0;
                    for (int j = 0; j < genres2.size(); j++) {
                        if (genres2.get(j).equals(genres.get(i))) {
                            countUniqueGenresInArray += 1;
                        }
                    }
                    if (countUniqueGenresInArray == 0) {
                        genres2.add(genres.get(i));
                    }
                    genres.remove(i);
                }
                onListOfFilmsListener.setListOfGenres(genres2);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFilms> call, @NonNull Throwable t) {
                onListOfFilmsListener.setError(t);
            }
        });
    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked, OnListOfFilmsListener onListOfFilmsListener) {
        List<Film> filmsCurrentGenre = new ArrayList<>();
        for (int i = 0; i < films.size(); i++) {
            for (int j = 0; j < films.get(i).getGenres().size(); j++) {
                if (films.get(i).getGenres().get(j).equals(genres2.get(position))) {
                    filmsCurrentGenre.add(films.get(i));
                }
            }
        }
        onListOfFilmsListener.setPressedGenreFilms(filmsCurrentGenre);
    }
}
