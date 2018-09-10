package com.example.nikita.filmbrowser.UI.Favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.nikita.filmbrowser.Room.Movie;
import com.example.nikita.filmbrowser.Room.MovieRepository;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import io.reactivex.Observable;

public class FavoritesViewModel extends AndroidViewModel {
    private MovieRepository mRepository;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((App) application).getRepository();
    }

    public Observable<List<Movie>> getFavorites(){
        return mRepository.getFavorites();
    }

    public void updateMovie(Movie movie){
        mRepository.updateMovie(movie);
    }

}
