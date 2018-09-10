package com.example.nikita.filmbrowser.UI;

import android.app.Application;

import com.example.nikita.filmbrowser.Room.MovieRepository;

public class App extends Application {

    public MovieRepository getRepository() {
        return MovieRepository.getInstance(this);
    }

}
