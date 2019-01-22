package com.example.nikita.filmbrowser.Domain.Interactors.Favorites;

import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FavoritesInteractor {

    @Inject
    public IMovieRepository movieRepository;

    public Observable<List<Movie>> getFavorites() {
        return new GetFavoritesUseCase(movieRepository).getFavorites();
    }

    public void updateMovie(Movie movie){
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }
}
