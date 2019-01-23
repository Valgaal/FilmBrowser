package com.example.nikita.filmbrowser.Domain.Interactors.Favorites;

import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.UI.MovieListModel;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FavoritesInteractor {

    @Inject
    public IMovieRepository movieRepository;

    public FavoritesInteractor() {
        App.getComponent().inject(this);
    }

    public Observable<List<MovieListModel>> getFavorites() {
        return new GetFavoritesUseCase(movieRepository)
                .getFavorites()
                .map(Converters::convertListToMovieListModel);
    }

    public void updateMovie(MovieListModel movie) {
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }
}
