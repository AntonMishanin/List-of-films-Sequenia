package com.example.listoffilmssequenia.data.ui.mainactivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.example.listoffilmssequenia.data.ui.films.ListFilmsFragment;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements OnBackClickListener,
        com.example.listoffilmssequenia.data.ui.mainactivity.contract.View,
        OnClickFilmListener, OnClickGenreListener {

    private static final String TAG_LIST_FILMS_FRAGMENT = "ListFilmsFragment";
    private static final String TAG_DETAILS_FILM_FRAGMENT = "DetailsFilmFragment";
    public static final String BUNDLE_KEY_CLICKED_FILM = "BUNDLE_KEY_CLICKED_FILM";
    public static final int DEFAULT_GENRE_NOT_SELECTED = -1;
    public static final int DEFAULT_FILM_NOT_SELECTED_BY_POSITION = -1;

    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private ListFilmsFragment listFilmsFragment;

    @Inject
    MvpPresenter mvpPresenter;
    @Inject
    Api api;
    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        initToolbar();
        startFragment();
        iniDagger();

        mvpPresenter.getListOfFilms();
    }

    private void iniDagger() {
        DaggerFilmsComponent.builder()
                .appComponent(getAppComponent())
                .filmsModule(new FilmsModule(this, sharedPreferences))
                .build()
                .inject(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClickFilm(int position) {
        mvpPresenter.onClickFilm(position);
        setSharedPreferences();
    }

    @Override
    public void oStartDetailsFilmFragment(Film film) {
        DetailsFilmFragment detailsFilmFragment = (DetailsFilmFragment) fragmentManager.findFragmentByTag(TAG_DETAILS_FILM_FRAGMENT);

        if (detailsFilmFragment != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_CLICKED_FILM, film);
            detailsFilmFragment.setArguments(bundle);

        } else {
            detailsFilmFragment = new DetailsFilmFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_CLICKED_FILM, film);
            detailsFilmFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_container, detailsFilmFragment, TAG_DETAILS_FILM_FRAGMENT);
            fragmentTransaction.addToBackStack(null).commit();
        }

        detailsFilmFragment.setListener(this);

    }

    @Override
    public void onClickGenre(int position, boolean isGenreChecked) {
        mvpPresenter.onClickGenre(position, isGenreChecked);
    }

    @Override
    public void setPressedGenreFilms(List<Film> filmsBySelectedGenre, int genrePositionSelected) {
        listFilmsFragment.setListFilmsIntoAdapter(filmsBySelectedGenre, this);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        listFilmsFragment.setListFilmsIntoAdapter(films, this);
    }

    @Override
    public void setListOfGenres(List<String> uniqueGenres) {
        listFilmsFragment.setListGenresIntoAdapter(uniqueGenres, this);
    }

    @Override
    public void setError(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setSharedPreferences() {
        if (mvpPresenter != null) {
            mvpPresenter.setSharedPreferences();
        }
    }

    @Override
    public void loadSharedPreferences(PrefModel prefModel, List<String> uniqueGenres) {
        int genrePositionSelected = prefModel.getGenrePositionSelected();
        if (genrePositionSelected != DEFAULT_GENRE_NOT_SELECTED && uniqueGenres.size() != 0) {
            listFilmsFragment.setSelectedGenre(genrePositionSelected, uniqueGenres);
        }

        if (prefModel.getPositionClickedFilm() != DEFAULT_FILM_NOT_SELECTED_BY_POSITION) {
            listFilmsFragment.restorationDetailsFragment(prefModel.getPositionClickedFilm());
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_fragment_details);
        setSupportActionBar(toolbar);
    }

    private void startFragment() {
        fragmentManager = getSupportFragmentManager();
        listFilmsFragment = (ListFilmsFragment) fragmentManager.findFragmentByTag(TAG_LIST_FILMS_FRAGMENT);

        if (listFilmsFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            listFilmsFragment = new ListFilmsFragment();
            fragmentTransaction.replace(R.id.frame_layout_container, listFilmsFragment, TAG_LIST_FILMS_FRAGMENT);
            fragmentTransaction.commit();
        }
        listFilmsFragment.initAdapter(this, this);
    }

    public void onBackPressedFromDetailFragment(View view) {
        onBackPressed();
        setUncheckedFilmPosition();
    }

    @Override
    public void onBackClick() {
        setUncheckedFilmPosition();
    }

    private void setUncheckedFilmPosition() {
        listFilmsFragment = (ListFilmsFragment) fragmentManager.findFragmentByTag(TAG_LIST_FILMS_FRAGMENT);
        if (listFilmsFragment != null) {
            mvpPresenter.setUncheckedFilmPosition(DEFAULT_FILM_NOT_SELECTED_BY_POSITION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setSharedPreferences();
    }
}
