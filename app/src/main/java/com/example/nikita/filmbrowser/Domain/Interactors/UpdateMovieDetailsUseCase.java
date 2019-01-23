package com.example.nikita.filmbrowser.Domain.Interactors;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.UI.MovieListModel;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateMovieDetailsUseCase {

    private IMovieRepository movieRepository;

    public UpdateMovieDetailsUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void updateMovie(MovieListModel movie) {
        Completable.fromAction(() -> {
            Movie movieFromDB = movieRepository.getMovieById(movie.getId()).blockingGet();
            movieFromDB.setFavorites(movie.isFavorites());
            movieFromDB.setTrending(movie.isTrending());
            movieRepository.updateMovie(movieFromDB);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
