package com.example.listoffilmssequenia.data.ui.films.contract;

import com.example.listoffilmssequenia.data.data.model.Film;

import java.util.List;

public interface View {

    void setListOfFilms(List<Film> films);

    void setListOfGenres(List<String> uniqueGenres);

    void setError(Throwable t);

    void setPressedGenreFilms(List<Film> filmsCurrentGenre);
}
