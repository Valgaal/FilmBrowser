package com.example.nikita.filmbrowser.Domain.Interactors.Search;

import com.example.nikita.filmbrowser.Domain.Interactors.InsertMovieUseCase;
import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Converters;
import com.example.nikita.filmbrowser.Models.MovieListModel;
import com.example.nikita.filmbrowser.UI.App;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SearchInteractor {

    @Inject
    public IMovieRepository movieRepository;

    public SearchInteractor() {
        App.getComponent().inject(this);
    }

    public void updateMovie(MovieListModel movie){
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }

    public void insertMovie(MovieListModel movie) {
       new InsertMovieUseCase(movieRepository).insertMovie(movie);
    }

    public Observable<List<MovieListModel>> searchMovie(String query){
        return new SearchMovieUseCase(movieRepository)
                .searchByApi(query)
                .map(Converters::convertListToMovieListModel);
    }
}
