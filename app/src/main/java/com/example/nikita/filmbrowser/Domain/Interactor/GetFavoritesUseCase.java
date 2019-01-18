package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import io.reactivex.Observable;

public class GetFavoritesUseCase extends BaseMoviesUseCase {

    public Observable<List<Movie>> getFavorites(){
        return movieRepository.getFavorites().toObservable();

    }
}
