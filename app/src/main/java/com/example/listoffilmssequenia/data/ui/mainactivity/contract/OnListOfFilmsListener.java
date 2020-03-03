package com.example.listoffilmssequenia.data.ui.mainactivity.contract;

import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.prefs.PrefModel;

import java.util.List;

public interface OnListOfFilmsListener {

    void setListOfFilms(List<Film> films);

    void setListOfGenres(List<String> uniqueGenres);

    void setError(Throwable t);

    void setPressedGenreFilms(List<Film> filmsBySelectedGenre, int genrePositionSelected);

    void oStartDetailsFilmFragment(Film film);

   void loadSharedPreferences(PrefModel prefModel, List<String> uniqueGenres);
}
