package com.example.nikita.filmbrowser.Di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Model.Repositories.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private Application application;

    public RepositoryModule(@NonNull Application application){
        this.application = application;
    }

    @Provides
    @NonNull
    @Singleton
    public MovieRepository provideMovieRepository(){
        return new MovieRepository(application);
    }
}
