package com.example.listoffilmssequenia.data.ui.films;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.prefs.PrefModel;
import com.example.listoffilmssequenia.data.ui.films.contract.OnListOfFilmsListener;
import com.example.listoffilmssequenia.data.ui.films.contract.Presenter;
import com.example.listoffilmssequenia.data.ui.films.contract.View;

import java.util.List;

public class MvpPresenter implements Presenter, OnListOfFilmsListener {

    private View view;
    private MvpInteractor mvpInteractor;

    public MvpPresenter(View view, MvpInteractor mvpInteractor) {
        this.view = view;
        this.mvpInteractor = mvpInteractor;
    }

    @Override
    public void getListOfFilms() {
        mvpInteractor.getListOfFilms(this);
    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked) {
        mvpInteractor.onClickGenre(position, isGenreChecked, this);
    }

    @Override
    public void setPressedGenreFilms(List<Film> filmsBySelectedGenre, int genrePositionSelected) {
        view.setPressedGenreFilms(filmsBySelectedGenre, genrePositionSelected);
    }

    @Override
    public void oStartDetailsFilmFragment(Film film) {
        view.oStartDetailsFilmFragment(film);
    }

    @Override
    public void loadSharedPreferences(PrefModel prefModel, List<String> uniqueGenres) {
        view.loadSharedPreferences(prefModel, uniqueGenres);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        view.setListOfFilms(films);
    }

    @Override
    public void setListOfGenres(List<String> uniqueGenres) {
        view.setListOfGenres(uniqueGenres);
    }

    @Override
    public void setError(Throwable t) {
        view.setError(t);
    }

    void setSharedPreferences(PrefModel prefModel) {
        mvpInteractor.setSharedPreferences(prefModel);
    }

    void getSharedPreferences() {
         mvpInteractor.getSharedPreferences(this);
    }

    public void onClickFilm(int position){
        mvpInteractor.onClickFilm(position, this);
    }

    public void setPosition(int position){
        mvpInteractor.setPosition(position);
    }

    @Override
    public void setView(View view) {
       // this.view = view;
    }
}
