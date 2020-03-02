package com.example.listoffilmssequenia.data.di.module;

import android.content.SharedPreferences;

import com.example.listoffilmssequenia.data.data.network.Api;
import com.example.listoffilmssequenia.data.data.prefs.PreferencesHelper;
import com.example.listoffilmssequenia.data.di.PerActivity;
import com.example.listoffilmssequenia.data.ui.adapter.FilmsAdapter;
import com.example.listoffilmssequenia.data.ui.adapter.GenresAdapter;
import com.example.listoffilmssequenia.data.ui.films.MvpInteractor;
import com.example.listoffilmssequenia.data.ui.films.MvpPresenter;
import com.example.listoffilmssequenia.data.ui.films.OnClickFilmListener;
import com.example.listoffilmssequenia.data.ui.films.OnClickGenreListener;
import com.example.listoffilmssequenia.data.ui.films.contract.View;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FilmsModule {

    private View view;
    private OnClickFilmListener onClickFilmListener;
    private OnClickGenreListener onClickGenreListener;
    private SharedPreferences sharedPreferences;

    public FilmsModule(View view, OnClickFilmListener onClickFilmListener,
                       OnClickGenreListener onClickGenreListener, SharedPreferences sharedPreferences) {
        this.view = view;
        this.onClickFilmListener = onClickFilmListener;
        this.onClickGenreListener = onClickGenreListener;
        this.sharedPreferences = sharedPreferences;
    }

    @PerActivity
    @Provides
    PreferencesHelper providePreferencesHelper() {
        return new PreferencesHelper(sharedPreferences);
    }

    @PerActivity
    @Provides
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @PerActivity
    @Provides
    MvpInteractor provideMvpInteractor(Api api, PreferencesHelper preferencesHelper) {
        return new MvpInteractor(api, preferencesHelper);
    }

    @PerActivity
    @Provides
    MvpPresenter provideMvpPresenter(MvpInteractor mvpInteractor) {
        return new MvpPresenter(view, mvpInteractor);
    }

    @PerActivity
    @Provides
    FilmsAdapter provideFilmsAdapter() {
        return new FilmsAdapter(onClickFilmListener);
    }

    @PerActivity
    @Provides
    GenresAdapter provideGenresAdapter() {
        return new GenresAdapter(onClickGenreListener);
    }
}
