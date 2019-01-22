package com.example.nikita.filmbrowser.Domain.Interactors.Search;

import com.example.nikita.filmbrowser.Domain.Interactors.InsertMovieUseCase;
import com.example.nikita.filmbrowser.Domain.Interactors.UpdateMovieDetailsUseCase;
import com.example.nikita.filmbrowser.Domain.Repositories.IMovieRepository;
import com.example.nikita.filmbrowser.Model.DB.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SearchInteractor {

    @Inject
    public IMovieRepository movieRepository;

    public void updateMovie(Movie movie){
        new UpdateMovieDetailsUseCase(movieRepository).updateMovie(movie);
    }

    public void insertMovie(Movie movie) {
       new InsertMovieUseCase(movieRepository).insertMovie(movie);
    }

    public Observable<List<Movie>> searchMovie(String query){
        return new SearchMovieUseCase(movieRepository).searchByApi(query);
    }
}
