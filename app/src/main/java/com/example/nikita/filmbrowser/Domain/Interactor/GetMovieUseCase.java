package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.MovieDetails;

import io.reactivex.Observable;

public class GetMovieUseCase extends BaseMoviesUseCase {

    public Observable<MovieDetails> getMovie(int id) {
        return movieRepository.getMovie(id).toObservable().onErrorResumeNext(throwable -> {//если нет в дб, то делает запрос
            return movieRepository.getMovieFromNetwork(id).toObservable().onErrorResumeNext(networkThrowable -> {
                return Observable.error(networkThrowable);
            });
        });
    }

}
