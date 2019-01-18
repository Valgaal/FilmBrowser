package com.example.nikita.filmbrowser.UI;

import android.app.Application;

import com.example.nikita.filmbrowser.Di.AppComponent;
import com.example.nikita.filmbrowser.Di.DaggerAppComponent;
import com.example.nikita.filmbrowser.Di.RepositoryModule;

public class App extends Application {
    private static AppComponent component;

    public static  AppComponent getComponent(){
        return component;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .repositoryModule(new RepositoryModule(this))
                .build();
    }

}
