package com.example.listoffilmssequenia.data.data.prefs;

import com.example.listoffilmssequenia.data.data.model.Film;

public class PrefModel {

    private int genrePositionSelected;
    private boolean detailsFilmFragmentStart;
    private Film film;
    private int positionClickedFilm;

    public PrefModel(int genrePositionSelected, boolean detailsFilmFragmentStart, Film film, int positionClickedFilm) {
        this.genrePositionSelected = genrePositionSelected;
        this.detailsFilmFragmentStart = detailsFilmFragmentStart;
        this.film = film;
        this.positionClickedFilm = positionClickedFilm;
    }

    public int getGenrePositionSelected() {
        return genrePositionSelected;
    }

    public boolean getDetailsFilmFragmentStart() {
        return detailsFilmFragmentStart;
    }

    public Film getFilm() {
        return film;
    }

    public int getPositionClickedFilm() {
        return positionClickedFilm;
    }
}
