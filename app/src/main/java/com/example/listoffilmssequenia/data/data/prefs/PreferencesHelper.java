package com.example.listoffilmssequenia.data.data.prefs;

import android.content.SharedPreferences;

import static com.example.listoffilmssequenia.data.ui.mainactivity.MainActivity.DEFAULT_FILM_NOT_SELECTED_BY_POSITION;
import static com.example.listoffilmssequenia.data.ui.mainactivity.MainActivity.DEFAULT_GENRE_NOT_SELECTED;

public class PreferencesHelper {

    private static final String KEY_GENRE_POSITION = "KEY_GENRE_POSITION";
    private static final String KEY_POSITION_CLICKED_FILM = "KEY_POSITION_CLICKED_FILM";

    private SharedPreferences sharedPreferences;

    public PreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setSharedPreferences(PrefModel prefModel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_GENRE_POSITION, prefModel.getGenrePositionSelected());
        editor.putInt(KEY_POSITION_CLICKED_FILM, prefModel.getPositionClickedFilm());
        editor.apply();
    }

    public void setPosition(int position){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_POSITION_CLICKED_FILM, position);
        editor.apply();
    }

    public PrefModel getSharedPreferences() {
        return new PrefModel(sharedPreferences.getInt(KEY_GENRE_POSITION, DEFAULT_GENRE_NOT_SELECTED),
                sharedPreferences.getInt(KEY_POSITION_CLICKED_FILM, DEFAULT_FILM_NOT_SELECTED_BY_POSITION));
    }
}
