package com.example.nikita.filmbrowser.Domain.Interactors.Trending;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import io.reactivex.Observable;

class GetTrendingDayUseCase{

    private IMovieRepository movieRepository;

    GetTrendingDayUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Observable<List<Movie>> getTrendingDay() {
        return movieRepository.getTrendingDay().toObservable();

    }
}
