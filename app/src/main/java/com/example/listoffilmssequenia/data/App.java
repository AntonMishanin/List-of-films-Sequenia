package com.example.listoffilmssequenia.data;

import android.app.Application;

import com.example.listoffilmssequenia.data.di.component.AppComponent;
import com.example.listoffilmssequenia.data.di.component.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
