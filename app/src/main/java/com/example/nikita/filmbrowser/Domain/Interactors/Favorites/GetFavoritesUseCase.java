package com.example.nikita.filmbrowser.Domain.Interactors.Favorites;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import io.reactivex.Observable;

class GetFavoritesUseCase{
    private IMovieRepository movieRepository;

    GetFavoritesUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Observable<List<Movie>> getFavorites() {
        return movieRepository.getFavorites().toObservable();
    }
}
