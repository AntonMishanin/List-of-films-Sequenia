package com.example.listoffilmssequenia.data.ui.mainactivity.contract;

public interface Presenter {

    void getListOfFilms();

    void onClickGenre(int position, boolean isGenreChecked);

    void setUncheckedFilmPosition(int position);

    void onClickFilm(int position);

    void setSharedPreferences();
}
