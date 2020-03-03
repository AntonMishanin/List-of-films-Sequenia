package com.example.listoffilmssequenia.data.ui.films;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.ui.adapter.FilmsAdapter;
import com.example.listoffilmssequenia.data.ui.adapter.GenresAdapter;
import com.example.listoffilmssequenia.data.ui.mainactivity.OnClickFilmListener;
import com.example.listoffilmssequenia.data.ui.mainactivity.OnClickGenreListener;

import java.util.List;
import java.util.Objects;

public class ListFilmsFragment extends Fragment {

    private static final String TOOLBAR_TITLE_FILMS = "Фильмы";

    private View view;
    private FilmsAdapter filmsAdapter;
    private GenresAdapter genresAdapter;

    public ListFilmsFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_of_films, container, false);

        initToolbar();
        initRecyclerView();

        return view;
    }

    public void initAdapter(OnClickFilmListener onClickFilmListener, OnClickGenreListener onClickGenreListener) {
        filmsAdapter = new FilmsAdapter(onClickFilmListener);
        genresAdapter = new GenresAdapter(onClickGenreListener);
    }

    public void setListFilmsIntoAdapter(List<Film> films, OnClickFilmListener onClickFilmListener) {
        if (filmsAdapter != null) {
            filmsAdapter.setFilms(films);
        } else {
            filmsAdapter = new FilmsAdapter(onClickFilmListener);
            filmsAdapter.setFilms(films);
        }
    }

    public void setListGenresIntoAdapter(List<String> genres, OnClickGenreListener onClickGenreListener) {
        if (genresAdapter != null) {
            genresAdapter.setGenres(genres);
        } else {
            genresAdapter = new GenresAdapter(onClickGenreListener);
            genresAdapter.setGenres(genres);
        }
    }

    public void restorationDetailsFragment(int positionClickedFilm) {
        filmsAdapter.restorationDetailsFragment(positionClickedFilm);
    }

    public void setSelectedGenre(int genrePositionSelected, List<String> uniqueGenres) {
        genresAdapter.setSelectedGenre(genrePositionSelected, uniqueGenres);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_fragment_details);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(TOOLBAR_TITLE_FILMS);
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewFilm = view.findViewById(R.id.recycler_view_films);
        recyclerViewFilm.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewFilm.setLayoutManager(gridLayoutManager);
        recyclerViewFilm.setAdapter(filmsAdapter);
        recyclerViewFilm.setSaveEnabled(true);

        RecyclerView recyclerViewGenre = view.findViewById(R.id.recycler_view_genres);
        recyclerViewGenre.setHasFixedSize(true);
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewGenre.setAdapter(genresAdapter);
    }
}
