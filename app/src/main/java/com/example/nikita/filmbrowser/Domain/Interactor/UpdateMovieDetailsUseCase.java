package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.Movie;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateMovieDetailsUseCase extends BaseMoviesUseCase {

    public void updateMovie(Movie movie){
        Completable.fromAction(() -> movieRepository.updateMovie(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
