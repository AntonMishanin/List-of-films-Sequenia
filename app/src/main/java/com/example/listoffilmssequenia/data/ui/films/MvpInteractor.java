package com.example.listoffilmssequenia.data.ui.films;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.model.ResponseFilms;
import com.example.listoffilmssequenia.data.data.network.Api;
import com.example.listoffilmssequenia.data.data.prefs.PrefModel;
import com.example.listoffilmssequenia.data.data.prefs.PreferencesHelper;
import com.example.listoffilmssequenia.data.ui.films.contract.Interactor;
import com.example.listoffilmssequenia.data.ui.films.contract.OnListOfFilmsListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.listoffilmssequenia.data.ui.films.ListFilmsFragment.DEFAULT_GENRE_NOT_SELECTED;

public class MvpInteractor implements Interactor {

    private List<Film> films = new ArrayList<>();
    private PreferencesHelper preferencesHelper;
    private List<Film> filmsBySelectedGenre = new ArrayList<>();
    private List<String> uniqueGenres = new ArrayList<>();
    private Api api;

    public MvpInteractor(Api api, PreferencesHelper preferencesHelper) {
        this.api = api;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void getListOfFilms(final OnListOfFilmsListener onListOfFilmsListener) {
        new DownloadListOfFilms(onListOfFilmsListener).execute();
    }

    private List<String> getListUniqueGenres(List<Film> films) {
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
            onListOfFilmsListener.setPressedGenreFilms(films, DEFAULT_GENRE_NOT_SELECTED);
            filmsBySelectedGenre.clear();
            filmsBySelectedGenre.addAll(films);
        } else {
            filmsBySelectedGenre = new ArrayList<>();
            for (int i = 0; i < films.size(); i++) {
                for (int j = 0; j < films.get(i).getGenres().size(); j++) {
                    if (films.get(i).getGenres().get(j).equals(uniqueGenres.get(position))) {
                        filmsBySelectedGenre.add(films.get(i));
                    }
                }
            }
            onListOfFilmsListener.setPressedGenreFilms(filmsBySelectedGenre, position);
        }
    }

    public void onClickFilm(int position, OnListOfFilmsListener onListOfFilmsListener) {
        if(filmsBySelectedGenre.size() != 0) {
            onListOfFilmsListener.oStartDetailsFilmFragment(filmsBySelectedGenre.get(position));
        }
    }

    void setSharedPreferences(PrefModel prefModel) {
        preferencesHelper.setSharedPreferences(prefModel);
    }

    void getSharedPreferences(OnListOfFilmsListener onListOfFilmsListener) {
        onListOfFilmsListener.loadSharedPreferences(preferencesHelper.getSharedPreferences(), uniqueGenres);
    }

    public void setPosition(int position){
        preferencesHelper.setPosition(position);
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadListOfFilms extends AsyncTask<Void, Void, Void> {

        OnListOfFilmsListener onListOfFilmsListener;

        DownloadListOfFilms(OnListOfFilmsListener onListOfFilmsListener) {
            this.onListOfFilmsListener = onListOfFilmsListener;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            api.getListOfFilms().enqueue(new Callback<ResponseFilms>() {
                @Override
                public void onResponse(@NonNull Call<ResponseFilms> call, @NonNull Response<ResponseFilms> response) {
                    if (response.body() != null) {
                        films.addAll(response.body().getFilms());
                        Collections.sort(films);

                        filmsBySelectedGenre.addAll(films);
                        onListOfFilmsListener.setListOfFilms(films);
                        onListOfFilmsListener.setListOfGenres(getListUniqueGenres(films));

                        onListOfFilmsListener.loadSharedPreferences(preferencesHelper.getSharedPreferences(), uniqueGenres);
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
