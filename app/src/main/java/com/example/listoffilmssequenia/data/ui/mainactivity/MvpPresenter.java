package com.example.listoffilmssequenia.data.ui.mainactivity;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.prefs.PrefModel;
import com.example.listoffilmssequenia.data.ui.mainactivity.contract.OnListOfFilmsListener;
import com.example.listoffilmssequenia.data.ui.mainactivity.contract.Presenter;
import com.example.listoffilmssequenia.data.ui.mainactivity.contract.View;

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


    @Override
    public void setSharedPreferences() {
        mvpInteractor.setSharedPreferences();
    }

    @Override
    public void onClickFilm(int position) {
        mvpInteractor.onClickFilm(position, this);
    }

    @Override
    public void setUncheckedFilmPosition(int position) {
        mvpInteractor.setUncheckedFilmPosition(position);
    }
}
