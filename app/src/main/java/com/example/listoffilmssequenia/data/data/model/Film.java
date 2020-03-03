package com.example.listoffilmssequenia.data.data.model;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable, Comparable<Film> {

    private String localized_name;
    private String name;
    private int year;
    private float rating;
    private String image_url;
    private String description;
    private List<String> genres;

    public Film(String localized_name, String name, int year, float rating, String image_url, String description) {
        this.localized_name = localized_name;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.image_url = image_url;
        this.description = description;
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public float getRating() {
        return rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public int compareTo(Film film) {
        return getLocalized_name().compareTo(film.getLocalized_name());
    }
}
