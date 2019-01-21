package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import io.reactivex.Observable;

public class GetTrendingDayUseCase extends BaseMoviesUseCase {

    public Observable<List<Movie>> getFavorites() {
        return movieRepository.getTrendingDay().toObservable();

    }
}
