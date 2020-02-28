package com.example.listoffilmssequenia.data.ui.films;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.model.ResponseFilms;
import com.example.listoffilmssequenia.data.data.network.Api;
import com.example.listoffilmssequenia.data.ui.films.contract.Interactor;
import com.example.listoffilmssequenia.data.ui.films.contract.OnListOfFilmsListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MvpInteractor implements Interactor {

    private List<Film> films = new ArrayList<>();
    private List<String> uniqueGenres = new ArrayList<>();
    private Api api;

    public MvpInteractor(Api api) {
        this.api = api;
    }

    @Override
    public void getListOfFilms(final OnListOfFilmsListener onListOfFilmsListener) {
        new DownloadListOfFilms(onListOfFilmsListener).execute();
    }

    private List<String> getListUniqueGenres(List<Film> films){
        List<String> allGenres = new ArrayList<>();

        for (int i = 0; i < films.size(); i++) {
            allGenres.addAll(films.get(i).getGenres());
        }

        for (int i = 0; i < allGenres.size(); i++) {
            int countUniqueGenresInArray = 0;
            for (int j = 0; j < uniqueGenres.size(); j++) {
                if (uniqueGenres.get(j).equals(allGenres.get(i))) {
                    countUniqueGenresInArray += 1;
                }
            }
            if (countUniqueGenresInArray == 0) {
                uniqueGenres.add(allGenres.get(i));
            }
        }
        return uniqueGenres;
    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked, OnListOfFilmsListener onListOfFilmsListener) {
        if (!isGenreChecked) {
            onListOfFilmsListener.setPressedGenreFilms(films);
        } else {
            List<Film> filmsBySelectedGenre = new ArrayList<>();
            for (int i = 0; i < films.size(); i++) {
                for (int j = 0; j < films.get(i).getGenres().size(); j++) {
                    if (films.get(i).getGenres().get(j).equals(uniqueGenres.get(position))) {
                        filmsBySelectedGenre.add(films.get(i));
                    }
                }
            }
            onListOfFilmsListener.setPressedGenreFilms(filmsBySelectedGenre);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadListOfFilms extends AsyncTask<Void, Void, Void>{

        OnListOfFilmsListener onListOfFilmsListener;

         DownloadListOfFilms(OnListOfFilmsListener onListOfFilmsListener) {
            this.onListOfFilmsListener = onListOfFilmsListener;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            api.getListOfFilms().enqueue(new Callback<ResponseFilms>() {
                @Override
                public void onResponse(@NonNull Call<ResponseFilms> call, @NonNull Response<ResponseFilms> response) {
                    if(response.body()!=null) {
                        films.addAll(response.body().getFilms());
                        onListOfFilmsListener.setListOfFilms(films);
                        onListOfFilmsListener.setListOfGenres(getListUniqueGenres(films));
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseFilms> call, @NonNull Throwable t) {
                    onListOfFilmsListener.setError(t);
                }
            });
            return null;
        }
    }
}
