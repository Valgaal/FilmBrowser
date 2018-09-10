package com.example.nikita.filmbrowser.UI.Search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import io.reactivex.Observable;

public class SearchViewModel extends AndroidViewModel {
    private MovieRepository mRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<List<Movie>> searchFilm(String query){
        return mRepository.searchByApi(query);
    }

    public void insertMovie(Movie movie){
        mRepository.insertMovie(movie);
    }

    public void updateMovie(Movie movie){
        mRepository.updateMovie(movie);
    }

}
