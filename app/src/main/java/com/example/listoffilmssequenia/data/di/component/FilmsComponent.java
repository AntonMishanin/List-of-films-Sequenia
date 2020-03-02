package com.example.listoffilmssequenia.data.di.component;

import com.example.listoffilmssequenia.data.di.PerActivity;
import com.example.listoffilmssequenia.data.di.module.FilmsModule;
import com.example.listoffilmssequenia.data.ui.films.ListFilmsFragment;

import dagger.Component;

@PerActivity
@Component(modules = FilmsModule.class, dependencies = AppComponent.class)
public interface FilmsComponent {

    void inject(ListFilmsFragment listFilmsFragment);
}
