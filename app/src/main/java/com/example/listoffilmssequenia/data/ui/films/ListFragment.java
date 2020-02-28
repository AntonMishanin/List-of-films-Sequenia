package com.example.listoffilmssequenia.data.ui.films;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.App;
import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.network.Api;
import com.example.listoffilmssequenia.data.di.component.AppComponent;
import com.example.listoffilmssequenia.data.di.component.DaggerFilmsComponent;
import com.example.listoffilmssequenia.data.di.module.FilmsModule;
import com.example.listoffilmssequenia.data.ui.details.DetailsFragment;
import com.example.listoffilmssequenia.data.ui.adapter.FilmsAdapter;
import com.example.listoffilmssequenia.data.ui.adapter.GenresAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class ListFragment extends Fragment implements OnClickFilmListener, OnClickGenreListener, com.example.listoffilmssequenia.data.ui.films.contract.View {

    private static final String TAG = "ListFragment";
    private static final String TOOLBAR_TITLE_FILMS = "Фильмы";

    private List<Film> allFilms = new ArrayList<>();
    private View view;

    @Inject
    FilmsAdapter filmsAdapter;
    @Inject
    GenresAdapter genresAdapter;
    @Inject
    MvpPresenter mvpPresenter;
    @Inject
    Api api;

    public ListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        iniDagger();
        initRecyclerView();
        initToolbar();

        mvpPresenter.getListOfFilms();

        return view;
    }

    private void iniDagger() {
        DaggerFilmsComponent.builder()
                .appComponent(getAppComponent())
                .filmsModule(new FilmsModule(this, this, this))
                .build()
                .inject(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private AppComponent getAppComponent() {
        return ((App) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent();
    }

    private void initToolbar(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_fragment_details);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(TOOLBAR_TITLE_FILMS);
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewFilm = view.findViewById(R.id.recycler_view_films);
        recyclerViewFilm.setHasFixedSize(true);
        recyclerViewFilm.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewFilm.setAdapter(filmsAdapter);

        RecyclerView recyclerViewGenre = view.findViewById(R.id.recycler_view_genres);
        recyclerViewGenre.setHasFixedSize(true);
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewGenre.setAdapter(genresAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClickFilm(int position) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Clicked film", allFilms.get(position));
        detailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_container, detailsFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked) {
        mvpPresenter.onClickGenre(position, isGenreChecked);
    }

    @Override
    public void setPressedGenreFilms(List<Film> filmsBySelectedGenre) {
        filmsAdapter.setFilms(filmsBySelectedGenre);
        allFilms.clear();
        allFilms.addAll(filmsBySelectedGenre);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        filmsAdapter.setFilms(films);
        allFilms.clear();
        allFilms.addAll(films);
    }

    @Override
    public void setListOfGenres(List<String> uniqueGenres) {
        genresAdapter.setGenres(uniqueGenres);
    }

    @Override
    public void setError(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvpPresenter.setView(null);
    }
}
