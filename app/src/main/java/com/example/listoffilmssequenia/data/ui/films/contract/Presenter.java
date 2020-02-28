package com.example.listoffilmssequenia.data.ui.films.contract;

public interface Presenter {

    void getListOfFilms();

    void onClickGenre(int position, boolean isGenreChecked);

    void setView(View view);
}
