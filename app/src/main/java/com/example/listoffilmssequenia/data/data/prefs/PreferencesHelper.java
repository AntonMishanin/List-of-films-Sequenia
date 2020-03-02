package com.example.listoffilmssequenia.data.data.prefs;

import android.content.SharedPreferences;

import com.example.listoffilmssequenia.data.data.model.Film;

import static com.example.listoffilmssequenia.data.ui.films.ListFilmsFragment.DEFAULT_GENRE_NOT_SELECTED;

public class PreferencesHelper {

    private static final String KEY_GENRE_POSITION = "KEY_GENRE_POSITION";
    private static final String KEY_DETAILS_FRAGMENT_START = "KEY_DETAILS_FRAGMENT_START";

    private SharedPreferences sharedPreferences;

    public PreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setSharedPreferences(PrefModel prefModel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_GENRE_POSITION, prefModel.getGenrePositionSelected());
        editor.putBoolean(KEY_DETAILS_FRAGMENT_START, prefModel.getDetailsFilmFragmentStart());

        editor.putString("3", prefModel.getFilm().getLocalized_name());
        editor.putString("4", prefModel.getFilm().getName());
        editor.putInt("6", prefModel.getFilm().getYear());
        editor.putFloat("5", prefModel.getFilm().getRating());
        editor.putString("2", prefModel.getFilm().getImage_url());
        editor.putString("1", prefModel.getFilm().getDescription());

        editor.putInt("7", prefModel.getPositionClickedFilm());
        editor.apply();
    }

    public PrefModel getSharedPreferences() {
        Film film = new Film(
                sharedPreferences.getString("3", "3"),
                sharedPreferences.getString("4", "4"),
                sharedPreferences.getInt("6", 6),
                sharedPreferences.getFloat("5", 5),
                sharedPreferences.getString("2", "2"),
                sharedPreferences.getString("1", "1")
        );

        return new PrefModel(sharedPreferences.getInt(KEY_GENRE_POSITION, DEFAULT_GENRE_NOT_SELECTED),
                sharedPreferences.getBoolean(KEY_DETAILS_FRAGMENT_START, false),
                film,
                sharedPreferences.getInt("7", -1));
    }
}
