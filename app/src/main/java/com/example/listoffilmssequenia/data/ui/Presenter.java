package com.example.listoffilmssequenia.data.ui;

public interface Presenter {

    void getListOfFilms();

    void onClickGenre(int position, boolean isGenreChecked);
}
