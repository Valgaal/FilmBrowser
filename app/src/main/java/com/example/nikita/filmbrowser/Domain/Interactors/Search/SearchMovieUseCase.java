package com.example.nikita.filmbrowser.Domain.Interactors.Search;

import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Model.DB.Movie;
import com.example.nikita.filmbrowser.Model.Network.Models.SearchModel;

import java.util.List;

import io.reactivex.Observable;

class SearchMovieUseCase {

    private IMovieRepository movieRepository;

    SearchMovieUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Observable<List<Movie>> searchByApi(String query) {
        return movieRepository.searchByApi(query)
                .map(SearchModel::getResults)
                .flatMap(searchResultModels ->
                        Observable.fromIterable(searchResultModels)
                                .map(item -> {
                                    Movie converted = Converters.convertToMovie(item);
                                    try {//эта проверка на случай того, если уже есть фильм, то тогда нужно узнать в избранном он или нет и обновить его
                                        Movie movie = movieRepository.getMovieById(item.getId()).blockingGet();
                                        converted.setFavorites(movie.isFavorites());
                                        return converted;
                                    } catch (Exception e) {
                                        movieRepository.insertMovie(converted);
                                        return converted;
                                    }
                                })
                )
                .toList()
                .toObservable();
    }

}
