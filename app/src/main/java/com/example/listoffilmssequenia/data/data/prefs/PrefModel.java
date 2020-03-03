package com.example.listoffilmssequenia.data.data.prefs;

public class PrefModel {

    private int genrePositionSelected;
    private int positionClickedFilm;

    public PrefModel(int genrePositionSelected, int positionClickedFilm) {
        this.genrePositionSelected = genrePositionSelected;
        this.positionClickedFilm = positionClickedFilm;
    }

    public int getGenrePositionSelected() {
        return genrePositionSelected;
    }

    public int getPositionClickedFilm() {
        return positionClickedFilm;
    }
}
