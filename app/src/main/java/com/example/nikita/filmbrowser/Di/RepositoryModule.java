package com.example.nikita.filmbrowser.Di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
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
    public IMovieRepository provideIMovieRepository(){
        return new MovieRepository(application);
    }
}
