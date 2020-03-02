package com.example.listoffilmssequenia.data.ui.films;


import android.annotation.TargetApi;
import android.content.SharedPreferences;
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
import com.example.listoffilmssequenia.data.data.prefs.PrefModel;
import com.example.listoffilmssequenia.data.data.prefs.PreferencesHelper;
import com.example.listoffilmssequenia.data.di.component.AppComponent;
import com.example.listoffilmssequenia.data.di.component.DaggerFilmsComponent;
import com.example.listoffilmssequenia.data.di.module.FilmsModule;
import com.example.listoffilmssequenia.data.ui.details.DetailsFilmFragment;
import com.example.listoffilmssequenia.data.ui.adapter.FilmsAdapter;
import com.example.listoffilmssequenia.data.ui.adapter.GenresAdapter;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;


public class ListFilmsFragment extends Fragment implements OnClickFilmListener, OnClickGenreListener,
        com.example.listoffilmssequenia.data.ui.films.contract.View {

    private static final String TAG = "ListFilmsFragment";
    private static final String TOOLBAR_TITLE_FILMS = "Фильмы";
    public static final String BUNDLE_KEY_CLICKED_FILM = "BUNDLE_KEY_CLICKED_FILM";
    public static final int DEFAULT_GENRE_NOT_SELECTED = -1;

    private int genrePositionSelected = DEFAULT_GENRE_NOT_SELECTED;
    private boolean detailsFilmFragmentStart = false;
    private SharedPreferences sharedPreferences;
    private View view;
   // FragmentManager fragmentManager;
    private Film film = new Film("", "", 1, 1, "", "");
    private int positionClickedFilm = -1;

    @Inject
    FilmsAdapter filmsAdapter;
    @Inject
    GenresAdapter genresAdapter;
    @Inject
    MvpPresenter mvpPresenter;
    @Inject
    Api api;
    @Inject
    PreferencesHelper preferencesHelper;

    public ListFilmsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        Log.d("dd", "onStart: "+genrePositionSelected);

      /*  sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);

        iniDagger();
        initRecyclerView();
        initToolbar();

        mvpPresenter.getListOfFilms();
        //getSharedPreferences();
*/
        return view;
    }

    private void iniDagger() {
        DaggerFilmsComponent.builder()
                .appComponent(getAppComponent())
                .filmsModule(new FilmsModule(this, this, this, sharedPreferences))
                .build()
                .inject(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private AppComponent getAppComponent() {
        return ((App) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent();
    }

    private void initToolbar() {
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

    @Override
    public void onClickFilm(int position) {
        mvpPresenter.onClickFilm(position);
        positionClickedFilm = position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void oStartDetailsFilmFragment(Film film) {
        DetailsFilmFragment detailsFilmFragment = new DetailsFilmFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_CLICKED_FILM, film);
        detailsFilmFragment.setArguments(bundle);

        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_container, detailsFilmFragment);
        fragmentTransaction.addToBackStack(null).commit();

        detailsFilmFragmentStart = true;
        setSharedPreferences(film);
    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked) {
        mvpPresenter.onClickGenre(position, isGenreChecked);
        genrePositionSelected = position;
    }

    @Override
    public void setPressedGenreFilms(List<Film> filmsBySelectedGenre, int genrePositionSelected) {
        this.genrePositionSelected = genrePositionSelected;
        filmsAdapter.setFilms(filmsBySelectedGenre);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        filmsAdapter.setFilms(films);
        getSharedPreferences();
    }

    @Override
    public void setListOfGenres(List<String> uniqueGenres) {
        genresAdapter.setGenres(uniqueGenres);
    }

    @Override
    public void setError(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }

    private void setSharedPreferences(Film film) {
        PrefModel prefModel = new PrefModel(genrePositionSelected, detailsFilmFragmentStart, film, positionClickedFilm);
        mvpPresenter.setSharedPreferences(prefModel);
    }

    private void getSharedPreferences() {
        mvpPresenter.getSharedPreferences();
    }

    @Override
    public void loadSharedPreferences(PrefModel prefModel, List<String> uniqueGenres) {
        genrePositionSelected = prefModel.getGenrePositionSelected();
        detailsFilmFragmentStart = prefModel.getDetailsFilmFragmentStart();
        Log.d("dd", "load shered" + genrePositionSelected);
        if (genrePositionSelected != DEFAULT_GENRE_NOT_SELECTED && uniqueGenres.size() != 0) {
            genresAdapter.setSelectedGenre(genrePositionSelected, uniqueGenres);
        }

        if (prefModel.getPositionClickedFilm() != -1){
            filmsAdapter.restorationDetailsFragment(prefModel.getPositionClickedFilm());
        }


            if (detailsFilmFragmentStart) {
                //  DetailsFilmFragment detailsFilmFragment = new DetailsFilmFragment();

                // Bundle bundle = new Bundle();
                // bundle.putSerializable(BUNDLE_KEY_CLICKED_FILM, prefModel.getFilm());
                // detailsFilmFragment.setArguments(bundle);

                // fragmentManager = getActivity().getSupportFragmentManager();
                //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  fragmentTransaction.replace(R.id.frame_layout_container, detailsFilmFragment);
                //  fragmentTransaction.commit();
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  setSharedPreferences(film);
        Log.d("dd", "onDestroy: "+genrePositionSelected);
    }
}
