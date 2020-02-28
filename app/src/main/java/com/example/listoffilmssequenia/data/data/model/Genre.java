package com.example.listoffilmssequenia.data.data.model;

public class Genre {

    String genre;
    boolean genreChecked;

    public Genre(String genre, boolean genreChecked) {
        this.genre = genre;
        this.genreChecked = genreChecked;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isGenreChecked() {
        return genreChecked;
    }

    public void setGenreChecked(boolean genreChecked) {
        this.genreChecked = genreChecked;
    }
}
