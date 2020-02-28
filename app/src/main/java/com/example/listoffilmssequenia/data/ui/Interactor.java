package com.example.listoffilmssequenia.data.ui;

public interface Interactor {

   void getListOfFilms(OnListOfFilmsListener onListOfFilmsListener);

   void onClickGenre(int position, boolean isGenreChecked, OnListOfFilmsListener onListOfFilmsListener);
}
