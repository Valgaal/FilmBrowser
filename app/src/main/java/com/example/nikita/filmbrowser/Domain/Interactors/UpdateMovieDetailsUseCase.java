package com.example.nikita.filmbrowser.Domain.Interactors;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateMovieDetailsUseCase{

    private IMovieRepository movieRepository;

    public UpdateMovieDetailsUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void updateMovie(Movie movie) {
        Completable.fromAction(() -> movieRepository.updateMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
