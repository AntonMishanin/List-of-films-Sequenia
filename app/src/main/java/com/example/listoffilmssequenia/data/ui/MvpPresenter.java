package com.example.listoffilmssequenia.data.ui;

import com.example.listoffilmssequenia.data.data.model.Film;

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
    public void setPressedGenreFilms(List<Film> filmsCurrentGenre) {
        view.setPressedGenreFilms(filmsCurrentGenre);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        view.setListOfFilms(films);
    }

    @Override
    public void setListOfGenres(List<String> genres2) {
        view.setListOfGenres(genres2);
    }

    @Override
    public void setError(Throwable t) {
        view.setError(t);
    }
}
