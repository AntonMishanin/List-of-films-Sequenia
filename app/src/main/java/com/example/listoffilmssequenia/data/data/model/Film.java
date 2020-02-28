package com.example.listoffilmssequenia.data.data.model;

import java.util.List;

public class Film {

    int id;
    String localized_name;
    String name;
    int year;
    float rating;
    String image_url;
    String description;
    List<String> genres;

    public int getId() {
        return id;
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
}
