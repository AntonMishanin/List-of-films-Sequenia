package com.example.listoffilmssequenia.data.data.model;

public class Genre {

   private String genre;
   private boolean genreChecked;

    public Genre(String genre, boolean genreChecked) {
        this.genre = genre;
        this.genreChecked = genreChecked;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isGenreChecked() {
        return genreChecked;
    }

    public void setGenreChecked(boolean genreChecked) {
        this.genreChecked = genreChecked;
    }
}
