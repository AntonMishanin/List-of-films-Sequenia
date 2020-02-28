package com.example.listoffilmssequenia.data.ui.films;

import com.example.listoffilmssequenia.data.data.model.Film;
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
    public void setPressedGenreFilms(List<Film> filmsBySelectedGenre) {
        view.setPressedGenreFilms(filmsBySelectedGenre);
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
    public void setView(View view) {
        this.view = view;
    }
}
