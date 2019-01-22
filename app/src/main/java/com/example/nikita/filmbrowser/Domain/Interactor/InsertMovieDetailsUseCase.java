package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.MovieDetails;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InsertMovieDetailsUseCase extends BaseMoviesUseCase {

    public void insertMovieDetails(MovieDetails movieDetails) {
        Completable.fromAction(() -> movieRepository.insertMovieDetails(movieDetails))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
