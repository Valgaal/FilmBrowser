package com.example.nikita.filmbrowser.Domain.Interactor;

import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import io.reactivex.Observable;

public class SearchUseCase extends BaseMoviesUseCase {

    public Observable<List<Movie>> searchByApi(String query){
        return api.getSearchResult(API_KEY, query)
                .map(searchModel -> searchModel.getResults())
                .flatMap(searchResultModels ->
                        Observable.fromIterable(searchResultModels)
                                .map(item -> {
                                    try {//эта проверка на случай того, если уже есть фильм, то тогда нужно узнать в избранном он или нет и обновить его
                                        Movie movie = dao.getMovieById(item.getId()).blockingGet();
                                        Movie converted = Converters.convertToMovie(item);
                                        converted.setFavorites(movie.isFavorites());
                                        return converted;
                                    }catch (Exception e){
                                        return Converters.convertToMovie(item);
                                    }
                                })
                )
                .toList()
                .toObservable() ;
    }
}
