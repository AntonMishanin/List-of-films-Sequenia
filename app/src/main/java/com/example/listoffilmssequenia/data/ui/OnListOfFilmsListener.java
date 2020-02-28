package com.example.listoffilmssequenia.data.ui;

import com.example.listoffilmssequenia.data.data.model.Film;

import java.util.List;

public interface OnListOfFilmsListener {

    void setListOfFilms(List<Film> films);

    void setListOfGenres(List<String> genres2);

    void setError(Throwable t);

    void setPressedGenreFilms(List<Film> filmsCurrentGenre);
}
