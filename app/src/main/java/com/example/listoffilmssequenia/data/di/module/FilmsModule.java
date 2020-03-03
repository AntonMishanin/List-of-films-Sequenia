package com.example.listoffilmssequenia.data.di.module;

import android.content.SharedPreferences;

import com.example.listoffilmssequenia.data.data.network.Api;
import com.example.listoffilmssequenia.data.data.prefs.PreferencesHelper;
import com.example.listoffilmssequenia.data.di.PerActivity;
import com.example.listoffilmssequenia.data.ui.mainactivity.MvpInteractor;
import com.example.listoffilmssequenia.data.ui.mainactivity.MvpPresenter;
import com.example.listoffilmssequenia.data.ui.mainactivity.contract.View;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FilmsModule {

    private View view;
    private SharedPreferences sharedPreferences;

    public FilmsModule(View view, SharedPreferences sharedPreferences) {
        this.view = view;
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
}
