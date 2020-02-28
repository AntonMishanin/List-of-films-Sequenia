package com.example.listoffilmssequenia.data.di.component;

import com.example.listoffilmssequenia.data.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Retrofit getRetrofit();
}
